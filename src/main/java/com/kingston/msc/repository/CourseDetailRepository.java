package com.kingston.msc.repository;

import com.kingston.msc.entity.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/7/22
 */
@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {
}
