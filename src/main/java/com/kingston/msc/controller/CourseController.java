package com.kingston.msc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/coursetestL")
    public List courseTestL(){
        ArrayList arrayList = new ArrayList();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        return arrayList;
    }

    @GetMapping("/courseFeign")
    public String courseTestFeign(){
        return "Course route works for courseFeign!";
    }
}
