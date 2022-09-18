package com.kingston.msc.service;

import com.kingston.msc.entity.*;
import com.kingston.msc.model.StudentPostDto;
import com.kingston.msc.model.StudentTransactionTracker;
import com.kingston.msc.model.TempStudentDetails;
import com.kingston.msc.model.VerifyStudentApplication;
import com.kingston.msc.rabbitmq.StudentRabbitMQSender;
import com.kingston.msc.repository.*;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
@Service
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TempStudentRepository tempStudentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private QualificationDetailRepository qualificationDetailRepository;

    @Autowired
    private CourseMembershipRepository courseMembershipRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
    private CourseDetailRepository courseDetailRepository;

    @Autowired
    private CourseOffersRepository courseOffersRepository;

    @Autowired
    private StudentRabbitMQSender studentRabbitMQSender;

    @Override
    public TempStudent saveTempStudent(TempStudentDetails tempStudentDetails) {

        TempStudent tempStudent = new TempStudent();
        tempStudent.setAddressLine1(tempStudentDetails.getAddressLine1());
        tempStudent.setAddressLine2(tempStudentDetails.getAddressLine2());
        tempStudent.setAddressLine3(tempStudentDetails.getAddressLine3());
        Audit audit = new Audit();
        audit.setStatus(0);
        audit.setCreatedDate(new Date());
        audit.setLastEditDate(new Date());
        tempStudent.setAudit(audit);

        Branch branch = new Branch();
        branch.setId(tempStudentDetails.getBranchId());
        tempStudent.setBranchId(branch);

        tempStudent.setCivilStatus(tempStudentDetails.getCivilStatus());

        Course course = new Course();
        course.setId(tempStudentDetails.getCourseId());
        tempStudent.setCourseId(course);

        CourseType courseType = new CourseType();
        courseType.setId(tempStudentDetails.getCourseTypeId());
        tempStudent.setCourseTypeId(courseType);

        tempStudent.setDateOfBirth(tempStudentDetails.getDob());
        tempStudent.setDescription(tempStudentDetails.getDescription());
        tempStudent.setEmail(tempStudentDetails.getEmail());
        tempStudent.setFullName(tempStudentDetails.getFullName());
        tempStudent.setGender(tempStudentDetails.getGender());
        tempStudent.setNationality(tempStudentDetails.getNationality());
        tempStudent.setNic(tempStudentDetails.getNic());
        tempStudent.setPostalCode(tempStudentDetails.getPostalCode());
        tempStudent.setTelephone1(tempStudentDetails.getTelephone1());
        tempStudent.setTelephone2(tempStudentDetails.getTelephone2());

        TempStudent tempStudent_new = tempStudentRepository.save(tempStudent);

        if (null == tempStudentDetails.getEducationalQualificationList()) {
            System.out.println("list is null...");
        }

        if (null != tempStudentDetails.getEducationalQualificationList() && !tempStudentDetails.getEducationalQualificationList().isEmpty()) {
            tempStudentDetails.getEducationalQualificationList().forEach(educationQualification -> {
                Qualification qualification = new Qualification();
                qualification.setAudit(audit);
                qualification.setEffectiveDate(educationQualification.getEffectiveDate());
                qualification.setOverallGrade(educationQualification.getOverallGrade());
                qualification.setQualificationName(educationQualification.getQualificationName());
                QualificationType qualificationType = new QualificationType();
                qualificationType.setId(educationQualification.getQualificationTypeId());
                qualification.setQualificationTypeId(qualificationType);
                qualification.setSchool(educationQualification.getSchool());
                qualification.setCertificate("dummy path"); // save file location
                qualification.setStartDate(educationQualification.getStartDate());

                qualification = qualificationRepository.save(qualification);

                QualificationDetail qualificationDetail = new QualificationDetail();
                qualificationDetail.setDescription("");
                qualificationDetail.setGrade(educationQualification.getGrade());
                qualificationDetail.setSubject(educationQualification.getSubject());
                qualificationDetail.setQualificationId(qualification);
                qualificationDetail.setTempStudentId(tempStudent_new);

                qualificationDetailRepository.save(qualificationDetail);
            });
        }

        return tempStudent_new;
    }

    @Override
    public List<VerifyStudentApplication> findNewStudentApplications(long courseId, int status, long branchId) {

        Query query = entityManager.createNativeQuery("SELECT " +
                "ts.id as temp_student_id, ts.full_name, ts.email, ts.nationality, CONCAT(ts.telephone1, \" | \", ts.telephone2) AS telephone, ts.nic, ts.gender, ts.created_date, " +
                "q.qualification_name, qt.qualification_type_name, q.school, q.overall_grade, q.effective_date, " +
                "c.id as course_id " +
                "FROM temp_student ts " +
                "INNER JOIN qualification_detail qd ON qd.temp_student_id=ts.id " +
                "INNER JOIN qualification q ON q.id=qd.qualification_id " +
                "INNER JOIN qualification_type qt ON qt.id=q.qualification_type_id " +
                "INNER JOIN course c ON c.id=ts.course_id " +
                "WHERE ts.status=(?) AND ts.course_id=(?) AND ts.branch_id =(?) " +
                "ORDER BY 1");

        query.setParameter(1, status);
        query.setParameter(2, courseId);
        query.setParameter(3, branchId);
        java.util.List<Object[]> list = query.getResultList();

        java.util.List<VerifyStudentApplication> newApplicationsList = null;

        if (!list.isEmpty()) {
            newApplicationsList = new ArrayList<>();
            for (Object[] obj : list) {
                VerifyStudentApplication application = new VerifyStudentApplication();
                application.setTempStudentId(Long.parseLong(obj[0].toString()));
                application.setFullName(obj[1].toString());
                application.setEmail(obj[2].toString());
                application.setNationality(obj[3].toString());
                application.setTelephone(obj[4].toString());
                application.setNic(obj[5].toString());
                application.setGender(obj[6].toString());
                application.setCreatedDate((Date) obj[7]);
                application.setQualificationName(obj[8].toString());
                application.setQualificationTypeName(obj[9].toString());
                application.setSchool(obj[10].toString());
                application.setOverallGrade(obj[11].toString());
                application.setEffectiveDate((Date) obj[12]);
                application.setCourseId(Long.parseLong(obj[13].toString()));

                newApplicationsList.add(application);
            }
        }

        return newApplicationsList;
    }

    @Override
    public List<TempStudent> findNewStudentApplications2(long courseId, int status, long branchId) {
        Course course = new Course();
        course.setId(courseId);

        Branch branch = new Branch();
        branch.setId(branchId);

        Audit audit = new Audit();
        audit.setStatus(0);
        List<TempStudent> tempStudentList = tempStudentRepository.findTempStudentsByCourseIdAndBranchId(course, branch);
        return tempStudentList;
    }

    @Override
    public List<VerifyStudentApplication> findPaymentCompletedApplications(long courseId, int status, long branchId) {

        Query query = entityManager.createNativeQuery("SELECT " +
                "ts.id as temp_student_id, ts.full_name, ts.email, ts.nationality, CONCAT(ts.telephone1, \" | \", ts.telephone2) AS telephone, ts.nic, ts.gender, ts.created_date, " +
                "q.qualification_name, qt.qualification_type_name, q.school, " +
                "c.id as course_id, tsp.amount, tsp.payment_date, " +
                "co.id as offer_id, co.offer " +
                "FROM temp_student ts " +
                "INNER JOIN qualification_detail qd ON qd.temp_student_id=ts.id " +
                "INNER JOIN qualification q ON q.id=qd.qualification_id " +
                "INNER JOIN qualification_type qt ON qt.id=q.qualification_type_id " +
                "INNER JOIN course c ON c.id=ts.course_id " +
                "INNER JOIN temp_student_payment tsp ON tsp.temp_student_id=ts.id " +
                "INNER JOIN course_offers co ON co.course_id=c.id " +
                "WHERE ts.status=(?) AND ts.course_id=(?) AND ts.branch_id =(?) " +
                "ORDER BY 1");

        query.setParameter(1, status);
        query.setParameter(2, courseId);
        query.setParameter(3, branchId);
        java.util.List<Object[]> list = query.getResultList();

        java.util.List<VerifyStudentApplication> newApplicationsList = null;

        if (!list.isEmpty()) {
            newApplicationsList = new ArrayList<>();
            for (Object[] obj : list) {
                VerifyStudentApplication application = new VerifyStudentApplication();
                application.setTempStudentId(Long.parseLong(obj[0].toString()));
                application.setFullName(obj[1].toString());
                application.setEmail(obj[2].toString());
                application.setNationality(obj[3].toString());
                application.setTelephone(obj[4].toString());
                application.setNic(obj[5].toString());
                application.setGender(obj[6].toString());
                application.setCreatedDate((Date) obj[7]);
                application.setQualificationName(obj[8].toString());
                application.setQualificationTypeName(obj[9].toString());
                application.setSchool(obj[10].toString());
                application.setCourseId(Long.parseLong(obj[11].toString()));
                application.setAmount(obj[12].toString());
                application.setPaymentDate((Date) obj[13]);
                application.setCourseOfferId(Long.parseLong(obj[14].toString()));
                application.setOfferName(obj[15].toString());

                newApplicationsList.add(application);
            }
        }

        return newApplicationsList;
    }

    @Override
    public Registration saveAndEnrollStudent(VerifyStudentApplication verifyStudentApplication) {

        log.info("Saving registration.. student name {}", verifyStudentApplication.getFullName());

//        get temp student object by id
        TempStudent tempStudent = tempStudentRepository.findById(verifyStudentApplication.getTempStudentId()).get();

        //get subject id list from time table by course id
        Course course = new Course();
        course.setId(verifyStudentApplication.getCourseId());
        List<TimeTable> timeTableList = timeTableRepository.findTimeTablesByCourseId(course);
        List<Subject> subjectList = new ArrayList<>();
        timeTableList.forEach(timeTable -> {
            System.out.println(timeTable.getSubjectId().getId());
            subjectList.add(timeTable.getSubjectId());
        });

        log.info("subject list size {} ", subjectList.size());

        // get course detail id
        CourseDetail courseDetail = courseDetailRepository.findCourseDetailByCourseIdAndCourseTypeId(course, tempStudent.getCourseTypeId());

        // get course offers
        CourseOffers courseOffers = courseOffersRepository.findCourseOffersByCourseId(course);

        // save student
        Student student = new Student();
        student.setAddressLine1(tempStudent.getAddressLine1());
        student.setAddressLine2(tempStudent.getAddressLine2());
        student.setAddressLine3(tempStudent.getAddressLine3());
        Audit audit = new Audit();
        audit.setStatus(1);
        audit.setCreatedDate(new Date());
        audit.setLastEditBy(verifyStudentApplication.getUsername());
        audit.setLastEditDate(new Date());
        student.setAudit(audit);
        student.setCivilStatus(tempStudent.getCivilStatus());
        student.setDateOfBirth(tempStudent.getDateOfBirth());
        student.setDescription(tempStudent.getDescription());
        student.setEmail(tempStudent.getEmail());
        student.setFullName(tempStudent.getFullName());
        student.setGender(tempStudent.getGender());
        student.setNationality(tempStudent.getNationality());
        student.setNic(tempStudent.getNic());
        student.setPostalCode(tempStudent.getPostalCode());
        student.setTelephone1(tempStudent.getTelephone1());
        student.setTelephone2(tempStudent.getTelephone2());
        student.setTempStudentId(tempStudent.getId());

        Student saved_student = studentRepository.save(student);

        // save registration
        Registration registration = new Registration();
        registration.setAudit(audit);
        Badges badges = new Badges();
        badges.setId(1L);
        registration.setBadgesId(badges);
        registration.setBranchId(tempStudent.getBranchId());
        registration.setCourseDetailId(courseDetail);
        registration.setCourseOffersId(courseOffers);
        registration.setRegistrationDate(new Date());
        registration.setSmsUserId(0L);

        Registration saved_reg = registrationRepository.save(registration);


        // save course membership
        List<CourseMembership> courseMembershipList = new ArrayList<>();
        Batch batch = new Batch();
        batch.setId(verifyStudentApplication.getBatchId());

        subjectList.forEach(subject -> {
            CourseMembership courseMembership = new CourseMembership();
            courseMembership.setBatchId(batch);
            courseMembership.setCourseId(course);
            courseMembership.setSubjectId(subject);
            courseMembership.setStudentId(saved_student);
            courseMembership.setRegistrationId(saved_reg);
            courseMembership.setAudit(audit);

            courseMembershipList.add(courseMembership);
        });

        courseMembershipRepository.saveAll(courseMembershipList);


        // publish msg to student reg queue
        StudentTransactionTracker studentTransactionTracker = new StudentTransactionTracker();
        studentTransactionTracker.setSmsAccountId(verifyStudentApplication.getSmsAccountId());
        studentTransactionTracker.setRegistrationId(saved_reg.getId()); //
        studentTransactionTracker.setStudentId(student.getId()); //
        studentTransactionTracker.setSubscription(Subscription.STUDENT.name());
        studentTransactionTracker.setEmail(student.getEmail());

        studentRabbitMQSender.sendToStudentQueue(studentTransactionTracker);

        return saved_reg;
    }

    @Override
    public long getRegistrationIdBySmsUserId(long smsUserId) {

        Registration registration = registrationRepository.findRegistrationBySmsUserId(smsUserId);
        if (null != registration) {
            return registration.getId();
        }
        return 0;
    }

    @Override
    public List<StudentPostDto> getStudentsByRegIds(List<Long> regIds) {

        Query query = entityManager.createNativeQuery("SELECT " +
                "distinct r.id as registrationId, s.full_name, s.email, cm.course_id, r.sms_user_id " +
                "FROM student s " +
                "INNER JOIN course_membership cm ON cm.student_id=s.id " +
                "INNER JOIN registration r ON r.id=cm.registration_id " +
                "WHERE r.id in(?1)");

        System.out.println(regIds.toString());

        String delim = ",";
        String res = String.join(delim, Arrays.asList(regIds.toString()));
        long[] result = regIds.stream().mapToLong(l -> l).toArray();

        query.setParameter(1, Arrays.asList(1, 2, 3, 4, 5));
        java.util.List<Object[]> list = query.getResultList();

        List<StudentPostDto> studentDataList = null;
        if (!list.isEmpty()) {
            studentDataList = new ArrayList<>();

            for (Object[] obj : list) {
                StudentPostDto studentPostDto = new StudentPostDto();
                studentPostDto.setRegistrationId(Long.parseLong(obj[0].toString()));
                studentPostDto.setFullName(obj[1].toString());
                studentPostDto.setEmail(obj[2].toString());
                studentPostDto.setCourseId(Long.parseLong(obj[3].toString()));
                studentPostDto.setSmsUSerId(Long.parseLong(obj[4].toString()));

                studentDataList.add(studentPostDto);
            }
        }
        return studentDataList;
    }

    @Override
    public int updateStatus(VerifyStudentApplication verifyStudentApplication) {

        Query query = entityManager.createNativeQuery("UPDATE temp_student SET status=(?) WHERE id=(?)");
        query.setParameter(1, verifyStudentApplication.getStatus());
        query.setParameter(2, verifyStudentApplication.getTempStudentId());
        int res = query.executeUpdate();
        return res;

//        TempStudent tempStudent = tempStudentRepository.findById(verifyStudentApplication.getTempStudentId()).get();
//        if(null != tempStudent){
//            System.out.println("===============");
//            //System.out.println(tempStudent);
//        }else {
//            System.out.println("aaa");
//        }


        //TempStudent tempStudent1 = new TempStudent();
        //tempStudent.setId(tempStudent.getId());

//        Audit audit = new Audit();
//        audit.setStatus(verifyStudentApplication.getStatus());
//        audit.setCreatedDate(new Date());
//        audit.setLastEditBy(verifyStudentApplication.getUsername());
//        audit.setLastEditDate(new Date());
//        tempStudent.setAudit(audit);
//
//        tempStudent = tempStudentRepository.save(tempStudent);

//        return tempStudent;
    }
}
