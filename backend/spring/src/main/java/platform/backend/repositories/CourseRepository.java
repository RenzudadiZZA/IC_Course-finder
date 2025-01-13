package platform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import platform.backend.entities.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByCourseCode(String courseCode);
    // Search by code and title
    @Query("SELECT c FROM Course c WHERE " +
            "LOWER(c.title) LIKE LOWER(CONCAT('%', :keywords, '%')) " +
            "OR LOWER(c.courseCode) = LOWER(:keywords)")
    List<Course> searchCourses(@Param("keywords") String keywords);

    // Search by course learning outcomes, module content, and description
    @Query("SELECT c FROM Course c WHERE " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.moduleContent) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.learningOutcomes) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Course> searchCoursesbyContent(@Param("keyword") String keyword);

}
