package com.kingston.msc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 10/18/21
 */

@RestController
@RequestMapping("/cps/course")
public class CourseController {

    @GetMapping("/coursetest")
    public String courseTest(){
        return "Course route works for student!";
    }
}
