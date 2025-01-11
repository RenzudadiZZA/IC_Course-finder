package platform.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.backend.entities.Course;
import platform.backend.repositories.CourseRepository;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // save course
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    // search course by course code
    public Course getCourseByCode(String courseCode) {
        return courseRepository.findById(courseCode).orElse(null);
    }

    // Delete course
    public void deleteCourse(String courseCode) {
        courseRepository.deleteById(courseCode);
    }
    
}
