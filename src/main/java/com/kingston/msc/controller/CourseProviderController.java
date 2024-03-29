package com.kingston.msc.controller;

import com.kingston.msc.entity.CPTransactionTracker;
import com.kingston.msc.entity.CourseProvider;
import com.kingston.msc.model.CourseProviderDetails;
import com.kingston.msc.rabbitmq.CourseProviderRabbitMQSender;
import com.kingston.msc.service.CourseProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/24/22
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/cps")
@Slf4j
public class CourseProviderController {

    @Autowired
    private CourseProviderService courseProviderService;

    @Autowired
    private CourseProviderRabbitMQSender rabbitMQSender;

    @GetMapping("/course-provider")
    public String testRoute() {
        System.out.println("test route working cp...");
        return "route is working";
    }

    @GetMapping("/test-rabbit")
    public String testRabbit() {
        CPTransactionTracker cpTransactionTracker = new CPTransactionTracker();
        cpTransactionTracker.setCourseProviderId("cp_id");

        rabbitMQSender.sendToCourseProviderQueue(cpTransactionTracker);
        return "route is working";
    }

    @PostMapping("/course-provider")
    public ResponseEntity<CourseProvider> registerCourseProvider(@RequestBody CourseProviderDetails courseProviderDetails) {

        System.out.println("cp registration route works!");
        CourseProvider courseProvider = courseProviderService.saveCourseProvider(courseProviderDetails);

        return ResponseEntity.ok().body(courseProvider);
    }


}
