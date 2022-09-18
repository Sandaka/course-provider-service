package com.kingston.msc.service;

import com.kingston.msc.entity.TempStudentPayment;
import com.kingston.msc.repository.TempStudentPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 9/13/22
 */
@Service
@Transactional
public class TempStudentPaymentServiceImpl implements TempStudentPaymentService {

    @Autowired
    private TempStudentPaymentRepository tempStudentPaymentRepository;

    @Override
    public TempStudentPayment savePayment(TempStudentPayment tempStudentPayment) {

        return tempStudentPaymentRepository.save(tempStudentPayment);
    }
}
