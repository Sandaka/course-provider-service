package com.kingston.msc.repository;

import com.kingston.msc.entity.Course;
import com.kingston.msc.entity.CourseDetail;
import com.kingston.msc.entity.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/7/22
 */
@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {

    CourseDetail findCourseDetailByCourseIdAndCourseTypeId(Course course, CourseType courseType);
}
