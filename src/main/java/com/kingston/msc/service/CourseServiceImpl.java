package com.kingston.msc.service;

import com.kingston.msc.entity.*;
import com.kingston.msc.model.CourseDetailsDto;
import com.kingston.msc.model.CourseFeeDto;
import com.kingston.msc.model.CourseYearFeeDto;
import com.kingston.msc.model.CourseYearFeeList;
import com.kingston.msc.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/7/22
 */
@Service
@Transactional
@Slf4j
public class CourseServiceImpl implements CourseService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
    private PaymentPlanRepository paymentPlanRepository;

    @Autowired
    private CourseOffersRepository courseOffersRepository;

    @Autowired
    private CourseDetailRepository courseDetailRepository;


    @Override
    public Course saveCourse(CourseDetailsDto courseDetailsDto) {

        // save course
        Course course = new Course();
        course.setDescription(courseDetailsDto.getDescription());
        CourseProvider courseProvider = new CourseProvider();
        courseProvider.setId(2L); // set dynamically
        course.setCourseProviderId(courseProvider);
        EducationLevel educationLevel = new EducationLevel();
        educationLevel.setId(courseDetailsDto.getEduLevelId());
        course.setEducationLevelId(educationLevel);
        course.setEndDate(courseDetailsDto.getEndDate());
        course.setSeats(courseDetailsDto.getSeats());
        course.setStartDate(courseDetailsDto.getStartDate());
        course.setTitle(courseDetailsDto.getCourseName());
        Audit audit = new Audit();
        audit.setStatus(0);
        audit.setCreatedDate(new Date());
        audit.setLastEditDate(new Date());
        course.setAudit(audit);

        final Course course_new = courseRepository.save(course);

        // save course detail
        CourseDetail courseDetail = new CourseDetail();
        courseDetail.setAudit(audit);
        courseDetail.setCourseFee(courseDetailsDto.getCourseFee());
        courseDetail.setCourseId(course);
        CourseType courseType = new CourseType();
        courseType.setId(courseDetailsDto.getCourseTypeId());
        courseDetail.setCourseTypeId(courseType);
        courseDetail.setDescription(courseDetailsDto.getDescription());
        courseDetail.setMedium(courseDetailsDto.getMedium());
        courseDetail.setYears(courseDetailsDto.getYears());

        final CourseDetail courseDetail_new = courseDetailRepository.save(courseDetail);

        // save time table
        List<TimeTable> timeTableList = new ArrayList<>();
        courseDetailsDto.getSubjectList().forEach(subjectDetail -> {
            TimeTable timeTable = new TimeTable();
            timeTable.setAudit(audit);
            timeTable.setCourseDetailId(courseDetail_new);
            timeTable.setCourseId(course_new);
            timeTable.setLectureDate(courseDetailsDto.getLectureDate());
            timeTable.setLectureTime(courseDetailsDto.getLectureTime());
            Subject subject = new Subject();
            subject.setId(subjectDetail.getId());
            timeTable.setSubjectId(subject);

            timeTableList.add(timeTable);
        });

        timeTableRepository.saveAll(timeTableList);


        // save payment plan
        List<PaymentPlan> paymentPlanList = new ArrayList<>();
        courseDetailsDto.getCourseYearsList().forEach(fee -> {
            PaymentPlan paymentPlan = new PaymentPlan();
            paymentPlan.setAudit(audit);
            paymentPlan.setCost(fee.getFee());
            paymentPlan.setCourseDetailId(courseDetail_new);
            paymentPlan.setDueDate(fee.getDueDate());
            paymentPlan.setYear(fee.getYear());

            paymentPlanList.add(paymentPlan);
        });

        paymentPlanRepository.saveAll(paymentPlanList);


        // save course offers
        if (null != courseDetailsDto.getOffer() && !courseDetailsDto.getOffer().isEmpty()) {
            CourseOffers courseOffers = new CourseOffers();
            courseOffers.setAudit(audit);
            courseOffers.setCourseId(course_new);
            courseOffers.setDescription(courseDetailsDto.getOfferDescription());
            courseOffers.setValidUntil(courseDetailsDto.getValidUntil());
            courseOffers.setOffer(courseDetailsDto.getOffer());

            courseOffersRepository.save(courseOffers);
        }

        return course_new;
    }

    @Override
    public List<Course> findCoursesByCourseProviderId(long courseProviderId) {
        CourseProvider courseProvider = new CourseProvider();
        courseProvider.setId(courseProviderId);

        List<Course> courseList = courseRepository.findCoursesByCourseProviderId(courseProvider);
        return courseList;
    }

    @Override
    public List<Course> findCoursesByCourseProviderIdAndEduLevelId(long courseProviderId, long educationLevelId) {
        CourseProvider courseProvider = new CourseProvider();
        courseProvider.setId(courseProviderId);
        EducationLevel educationLevel = new EducationLevel();
        educationLevel.setId(educationLevelId);

        List<Course> courseList = courseRepository.findCoursesByCourseProviderIdAndEducationLevelId(courseProvider, educationLevel);
        return courseList;
    }

    @Override
    public CourseYearFeeList findCourseDetailsForStudentRegistration(long courseId) {

        Query query = entityManager.createNativeQuery("SELECT\n" +
                "distinct pp.due_date, c.id AS course_id, c.title, c.start_date, c.end_date, c.seats, " +
                "ct.course_type_name, " +
                "el.id AS edu_level_id, el.edu_level, " +
                "cd.years, cd.medium, cd.course_fee, " +
                "co.offer, " +
                "pp.year, pp.cost, " +
                "CONCAT(tt.lecture_time,\" | \", tt.lecture_date) AS schedule " +
                "FROM course c " +
                "INNER JOIN course_detail cd on c.id=cd.course_id " +
                "INNER JOIN course_type ct on ct.id=cd.course_type_id " +
                "INNER JOIN payment_plan pp on pp.course_detail_id=cd.id " +
                "INNER JOIN time_table tt on tt.course_detail_id=cd.id " +
                "LEFT JOIN course_offers co on co.course_id=c.id " +
                "INNER JOIN education_level el on el.id=c.education_level_id " +
                "WHERE c.id=(?) " +
                "ORDER BY pp.due_date, ct.course_type_name");

        query.setParameter(1, courseId);
        List<Object[]> list = query.getResultList();

        CourseYearFeeList courseDetailsListAsObj = null;
        List<CourseYearFeeDto> courseYearFeeDtoList = new ArrayList<>();
        List<CourseFeeDto> courseFeeDtoList = new ArrayList<>();

        if (!list.isEmpty()) {
            courseDetailsListAsObj = new CourseYearFeeList();
            for (Object[] obj : list) {
                CourseYearFeeDto courseYearFeeDto = new CourseYearFeeDto();
                courseYearFeeDto.setCourseId(((BigInteger) obj[1]).longValue());
                courseYearFeeDto.setTitle(obj[2].toString());
                courseYearFeeDto.setStartDate((Date) obj[3]);
                courseYearFeeDto.setEndDate((Date) obj[4]);
                courseYearFeeDto.setSeats(obj[5].toString());
                courseYearFeeDto.setCourseTypeName(obj[6].toString());
                courseYearFeeDto.setEduLevelId(((BigInteger) obj[7]).longValue());
                courseYearFeeDto.setEduLevel(obj[8].toString());
                courseYearFeeDto.setYears(obj[9].toString());
                courseYearFeeDto.setMedium(obj[10].toString());
                courseYearFeeDto.setCourseFee(obj[11].toString());
                courseYearFeeDto.setOffer((obj[12] == null) ? "" : obj[12].toString());
                courseYearFeeDto.setSchedule(obj[15].toString());

                courseYearFeeDtoList.add(courseYearFeeDto);

                CourseFeeDto courseFeeDto = new CourseFeeDto();
                courseFeeDto.setDueDate(obj[0].toString());
                courseFeeDto.setYear(obj[13].toString());
                courseFeeDto.setCost((BigDecimal) obj[14]);

                courseFeeDtoList.add(courseFeeDto);
            }

            courseDetailsListAsObj.setCourseDetailsList(courseYearFeeDtoList);
            courseDetailsListAsObj.setCourseFeeList(courseFeeDtoList);
        }
        return courseDetailsListAsObj;
    }
}
