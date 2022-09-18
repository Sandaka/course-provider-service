package com.kingston.msc.controller;

import com.kingston.msc.model.StudentPostDto;
import com.kingston.msc.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 9/8/22
 */

@RestController
@RequestMapping("/cps")
@Slf4j
public class FeignRoutesController {

    @Autowired
    private StudentService studentService;


    @GetMapping("/reg_id/{id}")
    public long getRegIdBySmsUserId(@PathVariable(value = "id") long id) {
        return studentService.getRegistrationIdBySmsUserId(id);
    }

    @GetMapping("/students_by_regIds")
    public ResponseEntity<List<StudentPostDto>> getStudentsByRegId() {

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        List<StudentPostDto> studentPostDtoList = studentService.getStudentsByRegIds(ids);

        return ResponseEntity.ok().body(studentPostDtoList);
    }
}
