package platform.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import platform.backend.entities.LikedCourse;
import platform.backend.repositories.LikedCourseRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikedCourseServiceTest {

    @InjectMocks
    private LikedCourseService likedCourseService;

    @Mock
    private LikedCourseRepository likedCourseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addLikedCourse_whenCourseNotAlreadyLiked_shouldAddCourse() {
        String username = "user1";
        String courseCode = "course1";

        when(likedCourseRepository.existsByUsernameAndCourseCode(username, courseCode)).thenReturn(false);

        likedCourseService.addLikedCourse(username, courseCode);

        verify(likedCourseRepository, times(1)).save(any(LikedCourse.class));
    }

    @Test
    void addLikedCourse_whenCourseAlreadyLiked_shouldThrowException() {
        String username = "user1";
        String courseCode = "course1";

        when(likedCourseRepository.existsByUsernameAndCourseCode(username, courseCode)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                likedCourseService.addLikedCourse(username, courseCode));

        assertEquals("Course is already in favorites.", exception.getMessage());
        verify(likedCourseRepository, never()).save(any(LikedCourse.class));
    }

    @Test
    void getLikedCourses_shouldReturnListOfLikedCourses() {
        String username = "user1";
        LikedCourse likedCourse = new LikedCourse();
        likedCourse.setUsername(username);
        likedCourse.setCourseCode("course1");
        likedCourse.setCreatedAt(LocalDateTime.now());

        when(likedCourseRepository.findByUsername(username)).thenReturn(Collections.singletonList(likedCourse));

        List<LikedCourse> likedCourses = likedCourseService.getLikedCourses(username);

        assertEquals(1, likedCourses.size());
        assertEquals("course1", likedCourses.get(0).getCourseCode());
        verify(likedCourseRepository, times(1)).findByUsername(username);
    }

    @Test
    void removeLikedCourse_whenCourseExists_shouldRemoveCourse() {
        String username = "user1";
        String courseCode = "course1";
        LikedCourse likedCourse = new LikedCourse();
        likedCourse.setUsername(username);
        likedCourse.setCourseCode(courseCode);

        when(likedCourseRepository.findByUsernameAndCourseCode(username, courseCode)).thenReturn(likedCourse);

        likedCourseService.removeLikedCourse(username, courseCode);

        verify(likedCourseRepository, times(1)).delete(likedCourse);
    }

    @Test
    void removeLikedCourse_whenCourseDoesNotExist_shouldThrowException() {
        String username = "user1";
        String courseCode = "course1";

        when(likedCourseRepository.findByUsernameAndCourseCode(username, courseCode)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                likedCourseService.removeLikedCourse(username, courseCode));

        assertEquals("Course not found in favorites.", exception.getMessage());
        verify(likedCourseRepository, never()).delete(any(LikedCourse.class));
    }
}
