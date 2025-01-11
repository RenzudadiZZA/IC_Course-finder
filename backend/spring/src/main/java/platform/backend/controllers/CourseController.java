package platform.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.backend.entities.Course;
import platform.backend.services.CourseService;

import java.util.List;

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
    @GetMapping("/{courseCode}/basic")
    public Course getCourseByCode(@PathVariable String courseCode) {
        return courseService.getCourseByCode(courseCode);
    }

    // Delete course
    @DeleteMapping("/{courseCode}")
    public void deleteCourse(@PathVariable String courseCode) {
        courseService.deleteCourse(courseCode);
    }
    // Get detailed course information
    @GetMapping("/{courseCode}/details")
    public ResponseEntity<Course> getCourseDetails(@PathVariable String courseCode) {
        Course course = courseService.getCourseByCode(courseCode);
        if (course != null) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }

}
