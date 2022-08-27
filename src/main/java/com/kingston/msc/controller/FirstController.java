package com.kingston.msc.controller;

import com.kingston.msc.entity.CPTransactionTracker;
import com.kingston.msc.rabbitmq.RabbitMQSender;
import com.kingston.msc.utility.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 10/18/21
 */

@RestController
@RequestMapping("/cps")
@Slf4j
public class FirstController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @GetMapping("/message")
    public String test() {
        return "CP service works...";
    }

    @GetMapping("/testvariable/{id}")
    public String testVar(@PathVariable String id) {
        return id;
    }

    @GetMapping("/rabbitCP")
    public String testRabbitCP() {
        CPTransactionTracker cpTransactionTracker = new CPTransactionTracker();
        cpTransactionTracker.setTransactionId("1");
        cpTransactionTracker.setSubscription("test queue");
        cpTransactionTracker.setCourseProviderId("1");

        rabbitMQSender.sendToCourseProviderQueue(cpTransactionTracker);
        return "CP Q service works...";
    }

    @GetMapping("/test-connection")
    public ResponseEntity<?> testCourseProvider() {

        log.info("executing test-connection...");

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("course provider is running")
                        .timeStamp(LocalDateTime.now().toString())
                        .data("UP")
                        .build()
        );
    }
}
