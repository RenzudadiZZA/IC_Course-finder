package platform.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.backend.entities.Course;
import platform.backend.services.CourseService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Add course
    @PostMapping
    public Course saveCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    // Search course by course code
    @GetMapping("/{courseCode}")
    public Course getCourseByCode(@PathVariable String courseCode) {
        return courseService.getCourseByCode(courseCode);
    }

    // Delete course
    @DeleteMapping("/{courseCode}")
    public void deleteCourse(@PathVariable String courseCode) {
        courseService.deleteCourse(courseCode);
    }
}
