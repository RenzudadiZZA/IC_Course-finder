package platform.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.backend.entities.User;
import platform.backend.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Check if username already exists
    public boolean registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    // Admin Registration
    public User registerAdmin(String staffId, String password) throws Exception {
        if (!isValidStaffId(staffId)) {
            System.out.println("Invalid staff ID: " + staffId);
            throw new Exception("Invalid staff ID. Registration failed.");
        }

        // Check existence of Admin User
        if (userRepository.existsByUsername(staffId)) {
            System.out.println("Staff ID already exists: " + staffId);
            throw new Exception("Staff ID already exists.");
        }

        System.out.println("Staff ID is unique, proceeding with registration: " + staffId);

        // Create admin user
        User admin = new User();
        admin.setUsername(staffId);
        admin.setPassword(password);
        admin.setRole("admin");

        return userRepository.save(admin);
    }

    // Validate StaffID
    private boolean isValidStaffId(String staffId) {
        System.out.println("Validating staff ID: " + staffId);
        boolean isValid = "1111".equals(staffId);
        System.out.println("Validation result for staff ID (" + staffId + "): " + isValid);
        return isValid;
    }

    // Validate login
    public boolean validateLogin(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
