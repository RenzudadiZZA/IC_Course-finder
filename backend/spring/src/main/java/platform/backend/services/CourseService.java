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

    // 获取所有课程
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 保存课程
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    // 根据课程代码查找课程
    public Course getCourseByCode(String courseCode) {
        return courseRepository.findById(courseCode).orElse(null);
    }

    // 删除课程
    public void deleteCourse(String courseCode) {
        courseRepository.deleteById(courseCode);
    }
}
