package platform.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import platform.backend.services.CourseService;
import platform.backend.entities.Course;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void testGetAllCourses() throws Exception {
        Course course = new Course();
        course.setCourseCode("COURSE123");
        course.setTitle("Test Course");
        course.setDescription("Test Description");

        when(courseService.getAllCourses()).thenReturn(Collections.singletonList(course));

        mockMvc.perform(get("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("COURSE123"))
                .andExpect(jsonPath("$[0].title").value("Test Course"));
    }

    @Test
    void testGetCourseDetails() throws Exception {
        String courseCode = "COURSE123";

        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setTitle("Test Course");
        course.setDescription("Test Description");

        when(courseService.getCourseByCode(courseCode)).thenReturn(course);

        mockMvc.perform(get("/api/courses/{courseCode}", courseCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseCode").value(courseCode))
                .andExpect(jsonPath("$.title").value("Test Course"));
    }

    @Test
    void testSearchCourses() throws Exception {
        String keywords = "Test";

        Course course = new Course();
        course.setCourseCode("COURSE123");
        course.setTitle("Test Course");
        course.setDescription("Description");

        when(courseService.searchCourses(keywords)).thenReturn(Collections.singletonList(course));

        mockMvc.perform(get("/api/courses/search")
                        .param("keywords", keywords)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("COURSE123"))
                .andExpect(jsonPath("$[0].title").value("Test Course"));
    }
}
