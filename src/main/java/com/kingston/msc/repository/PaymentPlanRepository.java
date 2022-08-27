package com.kingston.msc.repository;

import com.kingston.msc.entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/7/22
 */
@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long> {
}
