package platform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platform.backend.entities.RecentlyVisitedCourse;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecentlyVisitedCourseRepository extends JpaRepository<RecentlyVisitedCourse, Long> {
    List<RecentlyVisitedCourse> findTop5ByUsernameOrderByVisitedAtDesc(String username);

    Optional<RecentlyVisitedCourse> findByUsernameAndCourseCode(String username, String courseCode);
}
