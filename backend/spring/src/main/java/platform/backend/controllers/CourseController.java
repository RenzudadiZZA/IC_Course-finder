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

    // Delete course
    @DeleteMapping("/{courseCode}")
    public void deleteCourse(@PathVariable String courseCode) {
        courseService.deleteCourse(courseCode);
    }
    // Get detailed course information
    @GetMapping("/{courseCode}")
    public ResponseEntity<Course> getCourseDetails(@PathVariable String courseCode) {
        Course course = courseService.getCourseByCode(courseCode);
        if (course != null) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }

    // Get course list for the InfoPage without requiring courseCode
    @GetMapping("/list")
    public List<Course> getCourseList() {
        return courseService.getAllCourses(); // This returns all courses relevant information
    }

}
