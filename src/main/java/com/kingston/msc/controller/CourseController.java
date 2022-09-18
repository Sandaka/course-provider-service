package com.kingston.msc.controller;

import com.kingston.msc.entity.Course;
import com.kingston.msc.entity.CourseProvider;
import com.kingston.msc.model.CourseDetailsDto;
import com.kingston.msc.model.CourseYearFeeList;
import com.kingston.msc.model.TempStudentCourseDetailDto;
import com.kingston.msc.service.CourseProviderService;
import com.kingston.msc.service.CourseService;
import com.kingston.msc.utility.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 10/18/21
 */

@RestController
@RequestMapping("/cps")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseProviderService courseProviderService;

    @GetMapping("/coursetest")
    public String courseTest() {
        return "Course route works for student!";
    }

    @GetMapping("/coursetestL")
    public List courseTestL() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        return arrayList;
    }

    @GetMapping("/courseFeign")
    public String courseTestFeign() {
        return "Course route works for courseFeign!";
    }

    @PostMapping("/course")
    public ResponseEntity<HttpResponse> saveCourse(@RequestBody CourseDetailsDto courseDetailsDto) {

        log.info("saving new course...{} ", courseDetailsDto.getCourseName());
        Course course = courseService.saveCourse(courseDetailsDto);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Saved")
                        .timeStamp(LocalDateTime.now().toString())
                        .data(course)
                        .build()
        );
    }

    @GetMapping("/courseStudent/{id}")
    public ResponseEntity<CourseYearFeeList> getCourseDetails(@PathVariable("id") long id) {
        CourseYearFeeList courseYearFeeList = courseService.findCourseDetailsForStudentRegistration(id);
        return ResponseEntity.ok().body(courseYearFeeList);
    }

    @GetMapping("/get_courses_by_cpid/{cpid}")
    public ResponseEntity<List<Course>> getAllCoursesByCPId(@PathVariable("cpid") long cpid) {

        List<Course> courseList = courseService.findCoursesByCourseProviderId(cpid);
        System.out.println(courseList.size() + "===============");
        return ResponseEntity.ok().body(courseList);
    }

    @GetMapping("/get_course_by_smsaccount_id/{smsaccountId}")
    public ResponseEntity<List<Course>> getAllCoursesBySmsAccountId(@PathVariable("smsaccountId") long smsaccountId) {

        CourseProvider courseProvider = courseProviderService.findCourseProviderBySmsAccountId(smsaccountId);
        List<Course> courseList = courseService.findCoursesByCourseProviderId(courseProvider.getId());
        return ResponseEntity.ok().body(courseList);
    }

    @GetMapping("/course_detail/{id}")
    public ResponseEntity<TempStudentCourseDetailDto> getCoursesByTempStudentId(@PathVariable("id") long tempStuId) {
        TempStudentCourseDetailDto tempStudentCourseDetailDto = courseService.findCourseDetailsByTempStuId(tempStuId);
        return ResponseEntity.ok().body(tempStudentCourseDetailDto);
    }

}
