package com.kingston.msc;

import com.kingston.msc.entity.Audit;
import com.kingston.msc.entity.CourseProvider;
import com.kingston.msc.model.CourseProviderDetails;
import com.kingston.msc.rabbitmq.CourseProviderRabbitMQSender;
import com.kingston.msc.repository.CPTransactionTrackerRepository;
import com.kingston.msc.repository.CourseProviderPaymentRepository;
import com.kingston.msc.repository.CourseProviderRepository;
import com.kingston.msc.service.CourseProviderService;
import com.kingston.msc.service.CourseProviderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.mockito.Mockito.times;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/31/22
 */
@ExtendWith(MockitoExtension.class)
public class CourseProviderServiceTest {

    @InjectMocks
    CourseProviderServiceImpl courseProviderService;

    @Mock
    private CourseProviderRepository courseProviderRepository;

    @Mock
    private CourseProviderPaymentRepository courseProviderPaymentRepository;

    @Mock
    private CPTransactionTrackerRepository transactionTrackerRepository;

    @Mock
    private CourseProviderRabbitMQSender rabbitMQSender;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCourseProvider_Test() {

        CourseProvider courseProvider = new CourseProvider();
        courseProvider.setAddressLine1("Horana");
        courseProvider.setAddressLine2("Horana");
        courseProvider.setAddressLine3("Horana");
        courseProvider.setDescription("test");
        courseProvider.setEmail("test@gmail.com");
        courseProvider.setFullName("Sandaka");
        courseProvider.setNationality("sri lankan");
        courseProvider.setNic("123456789V");
        courseProvider.setPostalCode("1234");

        courseProvider.setRegistrationNo("1234R");
        courseProvider.setSchoolName("my school");
        courseProvider.setSmsAccountId(0L);
        courseProvider.setTelephone1("1234567890");
        courseProvider.setTelephone2("1234567890");
        courseProvider.setWebsite("esoft.lk");
        Audit audit = new Audit();
        audit.setStatus(0);
        audit.setCreatedDate(new Date());
        audit.setLastEditDate(new Date());
        courseProvider.setAudit(audit);
        courseProvider.setId(1L);

        courseProviderService.saveCourseProvider(new CourseProviderDetails());
        Mockito.verify(courseProviderRepository, times(1)).save(courseProvider);
    }
}
