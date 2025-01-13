package platform.backend.servicesTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import platform.backend.entities.Course;
import platform.backend.repositories.CourseRepository;
import platform.backend.services.CourseService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCourses() {
        // Arrange
        Course course1 = new Course();
        course1.setCourseCode("BIOE40001");
        course1.setTitle("Bioengineering Science 1");

        Course course2 = new Course();
        course2.setCourseCode("COMP60001");
        course2.setTitle("Software Engineering");

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        // Act
        List<Course> courses = courseService.getAllCourses();

        // Assert
        assertNotNull(courses);
        assertEquals(2, courses.size());
        assertEquals("BIOE40001", courses.getFirst().getCourseCode());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testSaveCourse() {
        // Arrange
        Course course = new Course();
        course.setCourseCode("COMP60001");
        course.setTitle("Software Engineering");

        when(courseRepository.save(course)).thenReturn(course);

        // Act
        Course savedCourse = courseService.saveCourse(course);

        // Assert
        assertNotNull(savedCourse);
        assertEquals("COMP60001", savedCourse.getCourseCode());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testGetCourseByCode() {
        // Arrange
        String courseCode = "BIOE40001";
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setTitle("Bioengineering Science 1");

        when(courseRepository.findById(courseCode)).thenReturn(Optional.of(course));

        // Act
        Course foundCourse = courseService.getCourseByCode(courseCode);

        // Assert
        assertNotNull(foundCourse);
        assertEquals("BIOE40001", foundCourse.getCourseCode());
        verify(courseRepository, times(1)).findById(courseCode);
    }

    @Test
    void testDeleteCourse() {
        // Arrange
        String courseCode = "BIOE40001";

        // Act
        courseService.deleteCourse(courseCode);

        // Assert
        verify(courseRepository, times(1)).deleteById(courseCode);
    }

    @Test
    void testGetTitleByCourseCode() {
        // Arrange
        String courseCode = "BIOE40001";
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setTitle("Bioengineering Science 1");

        when(courseRepository.findByCourseCode(courseCode)).thenReturn(course);

        // Act
        String title = courseService.getTitleByCourseCode(courseCode);

        // Assert
        assertNotNull(title);
        assertEquals("Bioengineering Science 1", title);
        verify(courseRepository, times(1)).findByCourseCode(courseCode);
    }

    @Test
    void testSearchCourses() {
        // Arrange
        String keywords = "engineering";
        Course course1 = new Course();
        course1.setCourseCode("BIOE40001");
        course1.setTitle("Bioengineering Science 1");

        when(courseRepository.searchCourses(keywords)).thenReturn(List.of(course1));

        // Act
        List<Course> courses = courseService.searchCourses(keywords);

        // Assert
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("BIOE40001", courses.getFirst().getCourseCode());
        verify(courseRepository, times(1)).searchCourses(keywords);
    }

    @Test
    void testSearchCoursesByContent() {
        // Arrange
        String keyword = "biology";
        Course course = new Course();
        course.setCourseCode("BIOE40001");
        course.setTitle("Bioengineering Science 1");

        when(courseRepository.searchCoursesbyContent(keyword)).thenReturn(List.of(course));

        // Act
        List<Course> courses = courseService.searchCoursesbyContent(keyword);

        // Assert
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("BIOE40001", courses.getFirst().getCourseCode());
        verify(courseRepository, times(1)).searchCoursesbyContent(keyword);
    }
}