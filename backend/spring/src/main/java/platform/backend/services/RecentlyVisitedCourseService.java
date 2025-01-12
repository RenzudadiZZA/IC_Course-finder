package platform.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.backend.entities.RecentlyVisitedCourse;
import platform.backend.repositories.RecentlyVisitedCourseRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecentlyVisitedCourseService {

    @Autowired
    private RecentlyVisitedCourseRepository recentlyVisitedCourseRepository;

    @Transactional
    public void saveRecentlyVisitedCourse(String username, String courseCode) {
        // Check if the course already exists for the user
        Optional<RecentlyVisitedCourse> existingCourse = recentlyVisitedCourseRepository.findByUsernameAndCourseCode(username, courseCode);

        if (existingCourse.isPresent()) {
            // Update visitedAt if the course already exists
            RecentlyVisitedCourse course = existingCourse.get();
            course.setVisitedAt(new Date());
            recentlyVisitedCourseRepository.save(course);
            System.out.println("Course updated: " + course);
        } else {
            // Save a new course if it does not exist
            RecentlyVisitedCourse newCourse = new RecentlyVisitedCourse();
            newCourse.setUsername(username != null ? username : "Guest");
            newCourse.setCourseCode(courseCode != null ? courseCode : "Unknown");
            newCourse.setVisitedAt(new Date());
            recentlyVisitedCourseRepository.save(newCourse);
            System.out.println("New course saved: " + newCourse);
        }
    }

    // Fetch recently visited courses
    public List<RecentlyVisitedCourse> getRecentlyVisitedCourses(String username) {
        List<RecentlyVisitedCourse> courses = recentlyVisitedCourseRepository
                .findTop5ByUsernameOrderByVisitedAtDesc(username);

        courses.forEach(course -> {
            if (course.getUsername() == null) {
                course.setUsername("Guest");
            }
            if (course.getCourseCode() == null) {
                course.setCourseCode("Unknown");
            }
            if (course.getVisitedAt() == null) {
                course.setVisitedAt(new Date());
            }
        });

        return courses;
    }
}
