package platform.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.backend.entities.LikedCourse;
import platform.backend.repositories.LikedCourseRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LikedCourseService {

    @Autowired
    private LikedCourseRepository likedCourseRepository;

    public void addLikedCourse(String username, String courseCode) {
        if (likedCourseRepository.existsByUsernameAndCourseCode(username, courseCode)) {
            throw new IllegalArgumentException("Course is already in favorites.");
        }
        LikedCourse likedCourse = new LikedCourse();
        likedCourse.setUsername(username);
        likedCourse.setCourseCode(courseCode);
        likedCourse.setCreatedAt(LocalDateTime.now());
        likedCourseRepository.save(likedCourse);
    }

    public List<LikedCourse> getLikedCourses(String username) {
        return likedCourseRepository.findByUsername(username);
    }

    public void removeLikedCourse(String username, String courseCode) {
        LikedCourse likedCourse = likedCourseRepository.findByUsernameAndCourseCode(username, courseCode);
        if (likedCourse != null) {
            likedCourseRepository.delete(likedCourse);
        } else {
            throw new IllegalArgumentException("Course not found in favorites.");
        }
    }
}
