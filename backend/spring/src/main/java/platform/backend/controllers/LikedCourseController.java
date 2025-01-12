package platform.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.backend.entities.Course;
import platform.backend.entities.LikedCourse;
import platform.backend.services.CourseService;
import platform.backend.services.LikedCourseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/likedcourses")
public class LikedCourseController {

    @Autowired
    private LikedCourseService likedCourseService;

    @Autowired
    private CourseService courseService;

    // Add a course to the user's favorites
    @PostMapping
    public ResponseEntity<String> addLikedCourse(@RequestBody LikedCourseRequest request) {
        try {
            likedCourseService.addLikedCourse(request.getUsername(), request.getCourseCode());
            return ResponseEntity.ok("Course added to favorites.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get the user's favorite courses
    @GetMapping("/{username}")
    public ResponseEntity<List<LikedCourse>> getLikedCourses(@PathVariable String username) {
        return ResponseEntity.ok(likedCourseService.getLikedCourses(username));
    }

    // Remove a course from the user's favorites
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeLikedCourse(@RequestBody LikedCourseRequest request) {
        if (request.getUsername() == null || request.getCourseCode() == null) {
            return ResponseEntity.badRequest().body("Username and course code must not be null.");
        }
        try {
            likedCourseService.removeLikedCourse(request.getUsername(), request.getCourseCode());
            return ResponseEntity.ok("Course removed from favorites.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to remove course: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the course.");
        }
    }
    @GetMapping("/likedlist/{username}")
    public ResponseEntity<List<Map<String, String>>> getLikedCoursesSimple(@PathVariable String username) {
        List<LikedCourse> likedCourses = likedCourseService.getLikedCourses(username);

        List<Map<String, String>> simpleCourses = likedCourses.stream().map(likedCourse -> {
            Course course = courseService.getCourseByCode(likedCourse.getCourseCode());
            if (course != null) {
                Map<String, String> courseDetails = new HashMap<>();
                courseDetails.put("courseCode", course.getCourseCode());
                courseDetails.put("title", course.getTitle());
                return courseDetails;
            } else {
                return null;
            }
        }).filter(Objects::nonNull).toList();

        return ResponseEntity.ok(simpleCourses);
    }

}

class LikedCourseRequest {
    private String username;
    private String courseCode;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
