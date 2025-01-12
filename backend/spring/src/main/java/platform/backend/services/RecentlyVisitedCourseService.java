package platform.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.backend.entities.RecentlyVisitedCourse;
import platform.backend.repositories.RecentlyVisitedCourseRepository;

import java.util.Date;
import java.util.List;

@Service
public class RecentlyVisitedCourseService {

    @Autowired
    private RecentlyVisitedCourseRepository recentlyVisitedCourseRepository;

    @Transactional
    public void saveRecentlyVisitedCourse(String username, String courseCode) {
        RecentlyVisitedCourse course = new RecentlyVisitedCourse();
        course.setUsername(username != null ? username : "Guest");
        course.setCourseCode(courseCode != null ? courseCode : "Unknown");
        course.setVisitedAt(new Date());
        recentlyVisitedCourseRepository.save(course);
        System.out.println("Course saved: " + course);
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
