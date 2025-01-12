package platform.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.backend.services.CourseService;
import platform.backend.services.RecentlyVisitedCourseService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/recently-visited")
public class RecentlyVisitedCourseController {

    @Autowired
    private RecentlyVisitedCourseService recentlyVisitedCourseService;

    @Autowired
    private CourseService courseService;

    // Store recently visited courses
    @PostMapping
    public ResponseEntity<Void> saveRecentlyVisitedCourse(@RequestBody RecentlyVisitedRequest request) {
        System.out.println("Received request: username=" + request.getUsername() + ", courseCode=" + request.getCourseCode());
        recentlyVisitedCourseService.saveRecentlyVisitedCourse(request.getUsername(), request.getCourseCode());
        return ResponseEntity.ok().build();
    }

    // Fetch recently visited courses DTO
    @GetMapping("/{username}")
    public ResponseEntity<List<RecentlyVisitedCourseDTO>> getRecentlyVisitedCourses(@PathVariable String username) {
        List<RecentlyVisitedCourseDTO> courses = recentlyVisitedCourseService.getRecentlyVisitedCourses(username)
                .stream()
                .map(course -> new RecentlyVisitedCourseDTO(
                        course.getCourseCode(),
                        getCourseTitle(course.getCourseCode())
                ))
                .toList();
        return ResponseEntity.ok(courses);
    }

    private String getCourseTitle(String courseCode) {
        return courseService.getTitleByCourseCode(courseCode);
    }
}

// DTO for request body
class RecentlyVisitedRequest {
    private String username;
    private String courseCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourseCode() {
        return courseCode;
    }

}

// DTO for recently visited course response
class RecentlyVisitedCourseDTO {
    private String courseCode;
    private String title;

    // Constructor
    public RecentlyVisitedCourseDTO(String courseCode, String title) {
        this.courseCode = courseCode;
        this.title = title;
    }

    // Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }
}