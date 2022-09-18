package com.kingston.msc.repository;

import com.kingston.msc.entity.Course;
import com.kingston.msc.entity.CourseOffers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/7/22
 */
@Repository
public interface CourseOffersRepository extends JpaRepository<CourseOffers, Long> {

    CourseOffers findCourseOffersByCourseId(Course course);
}
