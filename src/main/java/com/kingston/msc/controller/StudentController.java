package com.kingston.msc.controller;

import com.kingston.msc.entity.TempStudent;
import com.kingston.msc.model.TempStudentDetails;
import com.kingston.msc.service.StudentService;
import com.kingston.msc.utility.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
}
