package platform.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> deleteCourse(@PathVariable String courseCode) {
        Course course = courseService.getCourseByCode(courseCode);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        courseService.deleteCourse(courseCode);
        return ResponseEntity.ok("Course deleted successfully");
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

    // search courses by keywords and course code
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam("keywords") String keywords) {
        System.out.println("Received search request with keywords: " + keywords);
        List<Course> courses = courseService.searchCourses(keywords);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/search/content")
    public ResponseEntity<List<Course>> searchCoursesbyContent(@RequestParam("keyword") String keyword) {
        List<Course> courses = courseService.searchCoursesbyContent(keyword);
        return ResponseEntity.ok(courses);
    }

}
