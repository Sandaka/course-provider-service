package com.kingston.msc.rabbitmq;

import com.kingston.msc.email.EmailService;
import com.kingston.msc.email.Mail;
import com.kingston.msc.entity.*;
import com.kingston.msc.model.StudentTransactionTracker;
import com.kingston.msc.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/27/22
 */
@Service
@Slf4j
public class RabbitMQConsumer {

//    @Value("${learngenix.rabbitmq.queue}")
//    private static final String QUEUE = "${learngenix.rabbitmq.queue}";

    @Autowired
    private CPTransactionTrackerRepository cpTransactionTrackerRepository;

    @Autowired
    private CourseProviderRepository courseProviderRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private TempStudentRepository tempStudentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = {"${learngenix.rabbitmq.cp.subscription.queue}"})
    public void receiveCourseProverSubscriptionMessage(CPTransactionTracker cpTransactionTracker) {
        System.out.println("cp Received Message from cp Queue CP id >>" + cpTransactionTracker.getCourseProviderId());
        updateCourseProvider(cpTransactionTracker);
    }

    @RabbitListener(queues = {"${learngenix.rabbitmq.stu.subscription.queue}"})
    public void receiveStudentSubscriptionMessage(StudentTransactionTracker studentTransactionTracker) {
        System.out.println("stu Received Message stu Items Queue CP id >>" + studentTransactionTracker.getStudentId());
        updateRegistrationAndTempStudent(studentTransactionTracker);
    }


    @Transactional
    protected void updateCourseProvider(CPTransactionTracker cpTransactionTracker) {
        if (!cpTransactionTracker.getSmsAccountId().equals("0")) {

            // update tt
            cpTransactionTrackerRepository.save(cpTransactionTracker);

            // update cp
            CourseProvider courseProvider = courseProviderRepository.findById(Long.parseLong(cpTransactionTracker.getCourseProviderId())).get();
            courseProvider.setId(Long.parseLong(cpTransactionTracker.getCourseProviderId()));
            courseProvider.setSmsAccountId(Long.parseLong(cpTransactionTracker.getSmsAccountId()));
            courseProviderRepository.save(courseProvider);

            // send email
            log.info("sending email...");
            Mail mail = new Mail();
            mail.setFrom("sandaka94@gmail.com");//replace with your desired email
            mail.setMailTo("travelduo94@gmail.com");//replace with your desired email
            mail.setSubject("Greetings from LearnGenix!");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("name", courseProvider.getSchoolName());
            model.put("location", "Sri Lanka");
            model.put("sign", "LearnGenix");
            model.put("role", "Course Provider");
            model.put("type", "NEWSLETTER");
            model.put("url", "http://localhost:4200/learngenix/signIn");
            model.put("reg_url", "http://localhost:4200/learngenix/student_registration/" + cpTransactionTracker.getCourseProviderId());
            model.put("username", cpTransactionTracker.getUsername());
            model.put("password", cpTransactionTracker.getPassword());
            mail.setProps(model);

            try {
                emailService.sendEmail(mail);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    protected void updateRegistrationAndTempStudent(StudentTransactionTracker studentTransactionTracker) {

        // update registration
        Audit audit = new Audit();
        audit.setStatus(1);
        audit.setCreatedDate(new Date());
        Registration registration = registrationRepository.findById(studentTransactionTracker.getRegistrationId()).get();
        registration.setSmsUserId(studentTransactionTracker.getSmsUserId());
        registration.setId(studentTransactionTracker.getRegistrationId());
        registration.setAudit(audit);

        registrationRepository.save(registration);

        // update temp student
        Student student = studentRepository.findById(studentTransactionTracker.getStudentId()).get();
        TempStudent tempStudent = tempStudentRepository.findById(student.getTempStudentId()).get();
        audit.setStatus(3);
        tempStudent.setAudit(audit);

        tempStudentRepository.save(tempStudent);

        // send email
        log.info("sending email...");
        Mail mail = new Mail();
        mail.setFrom("sandaka94@gmail.com");//replace with your desired email
        mail.setMailTo("travelduo94@gmail.com");//replace with your desired email
        mail.setSubject("Greetings from LearnGenix!");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", student.getFullName());
        model.put("location", "Sri Lanka");
        model.put("sign", "LearnGenix");
        model.put("role", "Student");
        model.put("type", "NEWSLETTER");
        model.put("url", "http://localhost:4200/learngenix/signIn");
        model.put("reg_url", "");
        model.put("username", studentTransactionTracker.getUsername());
        model.put("password", studentTransactionTracker.getPassword());
        mail.setProps(model);

        try {
            emailService.sendEmail(mail);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}
