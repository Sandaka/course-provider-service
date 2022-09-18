package com.kingston.msc.controller;

import com.kingston.msc.email.EmailService;
import com.kingston.msc.email.Mail;
import com.kingston.msc.entity.Audit;
import com.kingston.msc.entity.Registration;
import com.kingston.msc.entity.TempStudent;
import com.kingston.msc.entity.TempStudentPayment;
import com.kingston.msc.model.TempStudentCourseDetailDto;
import com.kingston.msc.model.TempStudentDetails;
import com.kingston.msc.model.VerifyStudentApplication;
import com.kingston.msc.service.StudentService;
import com.kingston.msc.service.TempStudentPaymentService;
import com.kingston.msc.utility.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
@RestController
@RequestMapping("/cps")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TempStudentPaymentService tempStudentPaymentService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/temp_student")
    public ResponseEntity<HttpResponse> saveTempStudent(@RequestBody TempStudentDetails tempStudentDetails) {

        log.info(tempStudentDetails.getTelephone1());

        // pass with certification
        TempStudent tempStudent = studentService.saveTempStudent(tempStudentDetails);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Saved")
                        .timeStamp(LocalDateTime.now().toString())
                        .data(tempStudent)
                        .build()
        );
    }

    @GetMapping("/applications")
    public ResponseEntity<List<VerifyStudentApplication>> getNewApplications(@RequestParam(name = "courseId") long courseId, @RequestParam(name = "branchId") long branchId) {
        List<VerifyStudentApplication> applications = studentService.findNewStudentApplications(courseId, 0, branchId);
        return ResponseEntity.ok().body(applications);
    }

    @GetMapping("/applications_payment")
    public ResponseEntity<List<VerifyStudentApplication>> getPaymentDoneApplications(@RequestParam(name = "courseId") long courseId, @RequestParam(name = "branchId") long branchId) {
        List<VerifyStudentApplication> applications = studentService.findPaymentCompletedApplications(courseId, 2, branchId);
        return ResponseEntity.ok().body(applications);
    }

    @GetMapping("/applications2")
    public ResponseEntity<List<TempStudent>> getNewApplications2(@RequestParam(name = "courseId") long courseId, @RequestParam(name = "branchId") long branchId) {
        List<TempStudent> applications = studentService.findNewStudentApplications2(courseId, 0, branchId);
        return ResponseEntity.ok().body(applications);
    }

    @PostMapping("/verify_payment")
    public ResponseEntity<HttpResponse> verifyPayments(@RequestBody VerifyStudentApplication verifyStudentApplication) {
        log.info("Found temp student {} > branch id {} > batch id {}", verifyStudentApplication.getTempStudentId(), verifyStudentApplication.getBranchId(), verifyStudentApplication.getBatchId());

        // pass with certification
        Registration registration = studentService.saveAndEnrollStudent(verifyStudentApplication);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Saved")
                        .timeStamp(LocalDateTime.now().toString())
                        .data(registration)
                        .build()
        );
    }

    @PostMapping("/verify_application")
    public ResponseEntity<HttpResponse> verifyApplication(@RequestBody VerifyStudentApplication verifyStudentApplication) {

        int res = studentService.updateStatus(verifyStudentApplication);

        if (res > 0) {
            // send email
            log.info("sending payment email...");
            Mail mail = new Mail();
            mail.setFrom("sandaka94@gmail.com");//replace with your desired email
            mail.setMailTo("travelduo94@gmail.com");//replace with your desired email
            mail.setSubject("Greetings from LearnGenix!");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("name", verifyStudentApplication.getFullName());
            model.put("location", "Sri Lanka");
            model.put("sign", "LearnGenix");
            model.put("role", "Student");
            model.put("type", "PAYMENTLINK");
            model.put("url", "http://localhost:4200/learngenix/registration_payment/" + verifyStudentApplication.getTempStudentId());
            mail.setProps(model);

            try {
                emailService.sendEmail(mail);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Updated")
                        .timeStamp(LocalDateTime.now().toString())
                        .data(res)
                        .build()
        );
    }

    @PostMapping("/reg_payment")
    public ResponseEntity<Integer> updateTempStudentInitPayment(@RequestBody TempStudentCourseDetailDto tempStudentCourseDetailDto) {
        VerifyStudentApplication verifyStudentApplication = new VerifyStudentApplication();
        verifyStudentApplication.setTempStudentId(tempStudentCourseDetailDto.getTempStuId());
        verifyStudentApplication.setStatus(2);
        int res = studentService.updateStatus(verifyStudentApplication);
        if (0 < res) {
            TempStudentPayment tempStudentPayment = new TempStudentPayment();
            TempStudent tempStudent = new TempStudent();
            tempStudent.setId(tempStudentCourseDetailDto.getTempStuId());
            tempStudentPayment.setTempStudentId(tempStudent);
            tempStudentPayment.setAmount(new BigDecimal(tempStudentCourseDetailDto.getCost()));
            tempStudentPayment.setPaymentDate(new Date());
            Audit audit = new Audit();
            audit.setStatus(1);
            audit.setCreatedDate(new Date());
            audit.setLastEditDate(new Date());
            tempStudentPayment.setAudit(audit);
            tempStudentPaymentService.savePayment(tempStudentPayment);
        }

        return ResponseEntity.ok().body(res);
    }
}
