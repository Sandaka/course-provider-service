package com.kingston.msc.repository;

import com.kingston.msc.entity.CourseProviderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/25/22
 */
@Repository
public interface CourseProviderPaymentRepository extends JpaRepository<CourseProviderPayment, Long> {
}
