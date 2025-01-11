package platform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import platform.backend.entities.Course;

public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByCourseCode(String courseCode);

}
