package platform.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import platform.backend.entities.User;
import platform.backend.services.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testRegister() throws Exception {
        when(userService.registerUser(any(User.class))).thenReturn(true);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\":\"testuser\"," +
                                "\"password\":\"password\"," +
                                "\"role\":\"student\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Registration successful"));
    }

    @Test
    void testRegisterAdmin() throws Exception {
        Map<String, String> adminRequest = new HashMap<>();
        adminRequest.put("staffId", "admin123");
        adminRequest.put("password", "password");

        User admin = new User();
        admin.setUsername("admin123");
        admin.setRole("admin");

        when(userService.registerAdmin("admin123", "password")).thenReturn(admin);

        mockMvc.perform(post("/api/users/registerAdmin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"staffId\":\"admin123\"," +
                                "\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Admin registration successful"))
                .andExpect(jsonPath("$.admin.username").value("admin123"))
                .andExpect(jsonPath("$.admin.role").value("admin"));
    }

    @Test
    void testLogin() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("student");

        when(userService.validateLogin("testuser", "password")).thenReturn(true);
        when(userService.getUserByUsername("testuser")).thenReturn(user);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"username\":\"testuser\"," +
                                "\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.role").value("student"));
    }

    @Test
    void testGetRole() throws Exception {
        when(userService.getRole("testuser")).thenReturn("student");

        mockMvc.perform(get("/api/users/getRole")
                        .param("keyword", "testuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("student"));
    }
}
