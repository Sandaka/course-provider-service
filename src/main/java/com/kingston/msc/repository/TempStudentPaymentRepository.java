package com.kingston.msc.repository;

import com.kingston.msc.entity.TempStudentPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 9/13/22
 */
@Repository
public interface TempStudentPaymentRepository extends JpaRepository<TempStudentPayment, Long> {
}
