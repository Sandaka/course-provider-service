package com.kingston.msc;

import com.kingston.msc.entity.Course;
import com.kingston.msc.entity.CourseProvider;
import com.kingston.msc.entity.EducationLevel;
import com.kingston.msc.repository.CourseRepository;
import com.kingston.msc.service.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/31/22
 */
@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    void findCoursesByCourseProviderId_Test() {
        CourseProvider courseProvider = new CourseProvider();
        courseProvider.setId(1L);

        List<Course> courseList = new ArrayList<>();
        Course course = new Course();
        course.setDescription("test");
        course.setSeats("30");
        course.setTitle("test course");

        courseList.add(course);

        Mockito.when(courseRepository.findCoursesByCourseProviderId(courseProvider)).thenReturn(courseList);

        //test
        List<Course> empList = courseService.findCoursesByCourseProviderId(1L);

        assertEquals(1, courseList.size());
        Mockito.verify(courseRepository, times(1)).findCoursesByCourseProviderId(courseProvider);
    }

    @Test
    void findCoursesByCourseProviderIdAndEduLevelId_Test() {
        CourseProvider courseProvider = new CourseProvider();
        courseProvider.setId(1L);

        EducationLevel educationLevel = new EducationLevel();
        educationLevel.setId(1L);

        List<Course> courseList = new ArrayList<>();
        Course course = new Course();
        course.setDescription("test");
        course.setSeats("30");
        course.setTitle("test course");

        courseList.add(course);

        Mockito.when(courseRepository.findCoursesByCourseProviderIdAndEducationLevelId(courseProvider, educationLevel)).thenReturn(courseList);

        //test
        List<Course> empList = courseService.findCoursesByCourseProviderIdAndEduLevelId(1L, 1L);

        assertEquals(1, courseList.size());
        Mockito.verify(courseRepository, times(1)).findCoursesByCourseProviderIdAndEducationLevelId(courseProvider, educationLevel);
    }
}
