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
import platform.backend.controllers.RecentlyVisitedCourseController;
import platform.backend.entities.RecentlyVisitedCourse;
import platform.backend.services.CourseService;
import platform.backend.services.RecentlyVisitedCourseService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecentlyVisitedCourseControllerTest {

    @Mock
    private RecentlyVisitedCourseService recentlyVisitedCourseService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private RecentlyVisitedCourseController recentlyVisitedCourseController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recentlyVisitedCourseController).build();
    }

    @Test
    void testSaveRecentlyVisitedCourse() throws Exception {
        // Arrange
        String requestBody = """
                {
                    "username": "testUser",
                    "courseCode": "BIOE40001"
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/recently-visited")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        // Verify
        verify(recentlyVisitedCourseService, times(1)).saveRecentlyVisitedCourse("testUser", "BIOE40001");
    }

    @Test
    void testGetRecentlyVisitedCourses() throws Exception {
        String username = "testUser";

        // Simulate here the RecentlyVisitedCourse and Course entities
        RecentlyVisitedCourse course1 = new RecentlyVisitedCourse();
        course1.setCourseCode("BIOE40001");
        RecentlyVisitedCourse course2 = new RecentlyVisitedCourse();
        course2.setCourseCode("COMP60001");

        when(recentlyVisitedCourseService.getRecentlyVisitedCourses(username))
                .thenReturn(Arrays.asList(course1, course2));

        when(courseService.getTitleByCourseCode("BIOE40001")).thenReturn("Bioengineering Science 1");
        when(courseService.getTitleByCourseCode("COMP60001")).thenReturn("Software Engineering");

        // Act & Assert
        mockMvc.perform(get("/api/recently-visited/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("BIOE40001"))
                .andExpect(jsonPath("$[0].title").value("Bioengineering Science 1"))
                .andExpect(jsonPath("$[1].courseCode").value("COMP60001"))
                .andExpect(jsonPath("$[1].title").value("Software Engineering"));

        // Verify
        verify(recentlyVisitedCourseService, times(1)).getRecentlyVisitedCourses(username);
        verify(courseService, times(1)).getTitleByCourseCode("BIOE40001");
        verify(courseService, times(1)).getTitleByCourseCode("COMP60001");
    }
}