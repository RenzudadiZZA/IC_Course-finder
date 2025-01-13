package platform.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.backend.entities.User;
import platform.backend.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.registerUser(user)) {
            return ResponseEntity.ok("Registration successful");
        }
        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("student");
        }
        return ResponseEntity.badRequest().body("Username already exists");
    }

    // Admin Registration
    @PostMapping("/registerAdmin")
    public ResponseEntity<Map<String, Object>> registerAdmin(@RequestBody Map<String, String> adminRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            String staffId = adminRequest.get("staffId");
            String password = adminRequest.get("password");

            User admin = userService.registerAdmin(staffId, password);
            response.put("message", "Admin registration successful");
            response.put("admin", admin);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Validate login
        if (userService.validateLogin(user.getUsername(), user.getPassword())) {
            // Get user information
            User userInfo = userService.getUserByUsername(user.getUsername());

            // Return user information
            Map<String, Object> response = new HashMap<>();
            response.put("username", userInfo.getUsername());
            response.put("role", userInfo.getRole());

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}
