package platform.backend.controllersTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import platform.backend.controllers.LikedCourseController;
import platform.backend.entities.Course;
import platform.backend.entities.LikedCourse;
import platform.backend.services.CourseService;
import platform.backend.services.LikedCourseService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LikedCourseControllerTest {

    @Mock
    private LikedCourseService likedCourseService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private LikedCourseController likedCourseController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(likedCourseController).build();
    }

    @Test
    void testAddLikedCourse() throws Exception {
        // Arrange
        String requestBody = """
                {
                    "username": "testUser",
                    "courseCode": "BIOE40001"
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/likedcourses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Course added to favorites."));

        // Verify
        verify(likedCourseService, times(1)).addLikedCourse("testUser", "BIOE40001");
    }

    @Test
    void testGetLikedCourses() throws Exception {
        // Arrange
        String username = "testUser";
        LikedCourse likedCourse1 = new LikedCourse();
        likedCourse1.setCourseCode("BIOE40001");

        LikedCourse likedCourse2 = new LikedCourse();
        likedCourse2.setCourseCode("COMP60001");

        when(likedCourseService.getLikedCourses(username)).thenReturn(Arrays.asList(likedCourse1, likedCourse2));

        // Act & Assert
        mockMvc.perform(get("/api/likedcourses/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("BIOE40001"))
                .andExpect(jsonPath("$[1].courseCode").value("COMP60001"));

        // Verify
        verify(likedCourseService, times(1)).getLikedCourses(username);
    }

    @Test
    void testRemoveLikedCourse() throws Exception {
        // Arrange
        String requestBody = """
                {
                    "username": "testUser",
                    "courseCode": "BIOE40001"
                }
                """;

        // Act & Assert
        mockMvc.perform(delete("/api/likedcourses/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Course removed from favorites."));

        // Verify
        verify(likedCourseService, times(1)).removeLikedCourse("testUser", "BIOE40001");
    }

    @Test
    void testGetLikedCoursesSimple() throws Exception {
        // Arrange
        String username = "testUser";
        LikedCourse likedCourse1 = new LikedCourse();
        likedCourse1.setCourseCode("BIOE40001");

        LikedCourse likedCourse2 = new LikedCourse();
        likedCourse2.setCourseCode("COMP60001");

        Course course1 = new Course();
        course1.setCourseCode("BIOE40001");
        course1.setTitle("Bioengineering Science 1");

        Course course2 = new Course();
        course2.setCourseCode("COMP60001");
        course2.setTitle("Software Engineering");

        when(likedCourseService.getLikedCourses(username)).thenReturn(Arrays.asList(likedCourse1, likedCourse2));
        when(courseService.getCourseByCode("BIOE40001")).thenReturn(course1);
        when(courseService.getCourseByCode("COMP60001")).thenReturn(course2);

        // Act & Assert
        mockMvc.perform(get("/api/likedcourses/likedlist/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("BIOE40001"))
                .andExpect(jsonPath("$[0].title").value("Bioengineering Science 1"))
                .andExpect(jsonPath("$[1].courseCode").value("COMP60001"))
                .andExpect(jsonPath("$[1].title").value("Software Engineering"));

        // Verify
        verify(likedCourseService, times(1)).getLikedCourses(username);
        verify(courseService, times(1)).getCourseByCode("BIOE40001");
        verify(courseService, times(1)).getCourseByCode("COMP60001");
    }
}