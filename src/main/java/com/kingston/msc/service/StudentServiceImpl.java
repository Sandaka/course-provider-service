package com.kingston.msc.service;

import com.kingston.msc.entity.*;
import com.kingston.msc.model.TempStudentDetails;
import com.kingston.msc.repository.QualificationDetailRepository;
import com.kingston.msc.repository.QualificationRepository;
import com.kingston.msc.repository.StudentRepository;
import com.kingston.msc.repository.TempStudentRepository;
import com.sun.tools.javac.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;

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

        if(null == tempStudentDetails.getEducationalQualificationList()){
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
    public List<TempStudentDetails> findNewStudentApplications(long courseId, long branchId, long courseTypeId) {
//        select
//        ts.full_name, ts.address_line1, ts.address_line2, ts.address_line3, ts.postal_code,
//                ts.telephone1, ts.telephone2, ts.dob, ts.gender,ts.email, ts.nationality
        return null;
    }
}
