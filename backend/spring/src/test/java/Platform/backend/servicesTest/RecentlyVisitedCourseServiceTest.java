
package platform.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import platform.backend.entities.RecentlyVisitedCourse;
import platform.backend.repositories.RecentlyVisitedCourseRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class RecentlyVisitedCourseServiceTest {

    @Mock
    private RecentlyVisitedCourseRepository recentlyVisitedCourseRepository;

    @InjectMocks
    private RecentlyVisitedCourseService recentlyVisitedCourseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRecentlyVisitedCourse_NewCourse() {
        String username = "testUser";
        String courseCode = "COURSE123";

        when(recentlyVisitedCourseRepository.findByUsernameAndCourseCode(username, courseCode))
                .thenReturn(Optional.empty());

        recentlyVisitedCourseService.saveRecentlyVisitedCourse(username, courseCode);

        verify(recentlyVisitedCourseRepository, times(1)).save(any(RecentlyVisitedCourse.class));
    }

    @Test
    void testSaveRecentlyVisitedCourse_ExistingCourse() {
        String username = "testUser";
        String courseCode = "COURSE123";

        RecentlyVisitedCourse existingCourse = new RecentlyVisitedCourse();
        existingCourse.setUsername(username);
        existingCourse.setCourseCode(courseCode);
        existingCourse.setVisitedAt(new Date());

        when(recentlyVisitedCourseRepository.findByUsernameAndCourseCode(username, courseCode))
                .thenReturn(Optional.of(existingCourse));

        recentlyVisitedCourseService.saveRecentlyVisitedCourse(username, courseCode);

        verify(recentlyVisitedCourseRepository, times(1)).save(existingCourse);
        assertNotNull(existingCourse.getVisitedAt());
    }

    @Test
    void testGetRecentlyVisitedCourses() {
        String username = "testUser";

        List<RecentlyVisitedCourse> courses = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            RecentlyVisitedCourse course = new RecentlyVisitedCourse();
            course.setUsername(username);
            course.setCourseCode("COURSE" + i);
            course.setVisitedAt(new Date());
            courses.add(course);
        }

        when(recentlyVisitedCourseRepository.findTop5ByUsernameOrderByVisitedAtDesc(username))
                .thenReturn(courses);

        List<RecentlyVisitedCourse> result = recentlyVisitedCourseService.getRecentlyVisitedCourses(username);

        assertEquals(5, result.size());
        assertEquals("COURSE1", result.get(0).getCourseCode());
        verify(recentlyVisitedCourseRepository, times(1)).findTop5ByUsernameOrderByVisitedAtDesc(username);
    }

    @Test
    void testGetRecentlyVisitedCourses_DefaultValues() {
        String username = "testUser";

        RecentlyVisitedCourse course = new RecentlyVisitedCourse();
        List<RecentlyVisitedCourse> courses = new ArrayList<>();
        courses.add(course);

        when(recentlyVisitedCourseRepository.findTop5ByUsernameOrderByVisitedAtDesc(username))
                .thenReturn(courses);

        List<RecentlyVisitedCourse> result = recentlyVisitedCourseService.getRecentlyVisitedCourses(username);

        assertEquals(1, result.size());
        assertEquals("Guest", result.get(0).getUsername());
        assertEquals("Unknown", result.get(0).getCourseCode());
        assertNotNull(result.get(0).getVisitedAt());
    }
}
