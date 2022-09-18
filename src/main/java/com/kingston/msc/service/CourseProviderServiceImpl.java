package com.kingston.msc.service;

import com.kingston.msc.entity.*;
import com.kingston.msc.model.CourseProviderDetails;
import com.kingston.msc.rabbitmq.CourseProviderRabbitMQSender;
import com.kingston.msc.repository.CPTransactionTrackerRepository;
import com.kingston.msc.repository.CourseProviderPaymentRepository;
import com.kingston.msc.repository.CourseProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/24/22
 */
@Service
@Transactional
@Slf4j
public class CourseProviderServiceImpl implements CourseProviderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CourseProviderRepository courseProviderRepository;

    @Autowired
    private CourseProviderPaymentRepository courseProviderPaymentRepository;

    @Autowired
    private CPTransactionTrackerRepository transactionTrackerRepository;

    @Autowired
    private CourseProviderRabbitMQSender rabbitMQSender;

    @Override
    public CourseProvider saveCourseProvider(CourseProviderDetails courseProviderDetails) {

        // insert data to course provider table
        CourseProvider courseProvider = new CourseProvider();
        courseProvider.setAddressLine1(courseProviderDetails.getAddressLine1());
        courseProvider.setAddressLine2(courseProviderDetails.getAddressLine2());
        courseProvider.setAddressLine3(courseProviderDetails.getAddressLine3());
        courseProvider.setDescription(courseProviderDetails.getDescription());
        courseProvider.setEmail(courseProviderDetails.getPersonalEmail());
        courseProvider.setFullName(courseProviderDetails.getFullName());
        courseProvider.setNationality(courseProviderDetails.getNationality());
        courseProvider.setNic(courseProviderDetails.getNic());
        courseProvider.setPostalCode(courseProviderDetails.getPostalCode());

        courseProvider.setRegistrationNo(courseProviderDetails.getRegistrationNo());
        courseProvider.setSchoolName(courseProviderDetails.getSchoolName());
        courseProvider.setSmsAccountId(0L);
        courseProvider.setTelephone1(courseProviderDetails.getTelephone1());
        courseProvider.setTelephone2(courseProviderDetails.getTelephone2());
        courseProvider.setWebsite(courseProviderDetails.getSchoolWebsite());
        Audit audit = new Audit();
        audit.setStatus(0);
        audit.setCreatedDate(new Date());
        audit.setLastEditDate(new Date());
        courseProvider.setAudit(audit);

        courseProvider = courseProviderRepository.save(courseProvider);


        // make payment stripe


        // insert data to cp payment table
        CourseProviderPayment courseProviderPayment = new CourseProviderPayment();
        courseProviderPayment.setAmount(courseProviderDetails.getAmount()); //
        audit.setStatus(1);
        courseProviderPayment.setAudit(audit);
        courseProviderPayment.setCourseProviderId(courseProvider);
        courseProviderPayment.setDescription("");
        courseProviderPayment.setTransactionDetail("");
        courseProviderPayment.setPaymentDate(new Date());

        courseProviderPayment = courseProviderPaymentRepository.save(courseProviderPayment);


        // insert data to cp_transaction_tracker and RabbitMQ
        CPTransactionTracker transactionTracker = new CPTransactionTracker();
        transactionTracker.setCourseProviderId(courseProvider.getId().toString());
        transactionTracker.setSmsAccountId("0");
        transactionTracker.setSubscription(Subscription.COURSE_PROVIDER.name());
        transactionTracker.setEmail(courseProviderDetails.getPersonalEmail());

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        log.info("Transaction tracker id {} for course provider {}", uuidAsString, courseProvider.getId());
        transactionTracker.setTransactionId(uuidAsString); // get from stripe

        transactionTracker = transactionTrackerRepository.save(transactionTracker);

        rabbitMQSender.sendToCourseProviderQueue(transactionTracker);


        return courseProvider;
    }

    @Override
    public CourseProvider findCourseProviderBySmsAccountId(Long smsAccountId) {

        return courseProviderRepository.findCourseProviderBySmsAccountId(smsAccountId);
    }
}
