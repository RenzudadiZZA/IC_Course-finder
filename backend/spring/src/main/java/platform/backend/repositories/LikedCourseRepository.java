package platform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import platform.backend.entities.LikedCourse;

import java.util.List;

public interface LikedCourseRepository extends JpaRepository<LikedCourse, Long> {
    List<LikedCourse> findByUsername(String username);
    boolean existsByUsernameAndCourseCode(String username, String courseCode);

    LikedCourse findByUsernameAndCourseCode(String username, String courseCode);
}
