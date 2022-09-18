package com.kingston.msc.service;

import com.kingston.msc.entity.Course;
import com.kingston.msc.model.CourseDetailsDto;
import com.kingston.msc.model.CourseYearFeeList;
import com.kingston.msc.model.TempStudentCourseDetailDto;

import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/7/22
 */
public interface CourseService {

    Course saveCourse(CourseDetailsDto courseDetailsDto);

    List<Course> findCoursesByCourseProviderId(long courseProviderId);

    List<Course> findCoursesByCourseProviderIdAndEduLevelId(long courseProviderId, long educationLevelId);

    CourseYearFeeList findCourseDetailsForStudentRegistration(long courseId);

    TempStudentCourseDetailDto findCourseDetailsByTempStuId(long tempStuId);
}
