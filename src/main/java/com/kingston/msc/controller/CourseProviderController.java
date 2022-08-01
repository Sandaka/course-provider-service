package com.kingston.msc.controller;

import com.kingston.msc.entity.CourseProvider;
import com.kingston.msc.model.CourseProviderDetails;
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
@RequestMapping("/cps")
@Slf4j
public class CourseProviderController {

    private CourseProviderService courseProviderService;

    @Autowired
    public CourseProviderController(CourseProviderService courseProviderService) {
        this.courseProviderService = courseProviderService;
    }

    @GetMapping("/course-provider")
    public String testRoute(){
        return "route is working";
    }

    @PostMapping("/course-provider")
    public ResponseEntity<CourseProvider> registerCourseProvider(@RequestBody CourseProviderDetails courseProviderDetails){

        System.out.println("cp registration route works!");

        return null;
    }


}
