package com.kingston.msc.repository;

import com.kingston.msc.entity.CourseProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/24/22
 */
@Repository
public interface CourseProviderRepository extends JpaRepository<CourseProvider, Long> {

    CourseProvider findCourseProviderBySmsAccountId(Long smsAccountId);
}
