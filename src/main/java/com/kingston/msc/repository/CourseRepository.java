package com.kingston.msc.repository;

import com.kingston.msc.entity.Course;
import com.kingston.msc.entity.CourseProvider;
import com.kingston.msc.entity.EducationLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/7/22
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findCoursesByCourseProviderId(CourseProvider courseProviderId);

    List<Course> findCoursesByCourseProviderIdAndEducationLevelId(CourseProvider courseProviderId, EducationLevel educationLevelId);
}
