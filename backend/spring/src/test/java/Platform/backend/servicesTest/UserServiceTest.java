package platform.backend.servicesTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import platform.backend.entities.User;
import platform.backend.repositories.UserRepository;
import platform.backend.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_UserExists() {
        User user = new User();
        user.setUsername("existingUser");

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        boolean result = userService.registerUser(user);

        assertFalse(result);
        verify(userRepository, never()).save(user);
    }

    @Test
    void testRegisterUser_UserDoesNotExist() {
        User user = new User();
        user.setUsername("newUser");

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);

        boolean result = userService.registerUser(user);

        assertTrue(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterAdmin_InvalidStaffId() {
        String staffId = "invalidId";
        String password = "password";

        Exception exception = assertThrows(Exception.class, () -> {
            userService.registerAdmin(staffId, password);
        });

        assertEquals("Invalid staff ID. Registration failed.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterAdmin_StaffIdExists() {
        String staffId = "1111";
        String password = "password";

        when(userRepository.existsByUsername(staffId)).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.registerAdmin(staffId, password);
        });

        assertEquals("Staff ID already exists.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterAdmin_Success() throws Exception {
        String staffId = "1111";
        String password = "password";

        when(userRepository.existsByUsername(staffId)).thenReturn(false);

        User admin = new User();
        admin.setUsername(staffId);
        admin.setPassword(password);
        admin.setRole("admin");

        when(userRepository.save(any(User.class))).thenReturn(admin);

        User result = userService.registerAdmin(staffId, password);

        assertNotNull(result);
        assertEquals(staffId, result.getUsername());
        assertEquals("admin", result.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testValidateLogin_Success() {
        String username = "user";
        String password = "password";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(user);

        boolean result = userService.validateLogin(username, password);

        assertTrue(result);
    }

    @Test
    void testValidateLogin_Failure() {
        String username = "user";
        String password = "password";

        when(userRepository.findByUsername(username)).thenReturn(null);

        boolean result = userService.validateLogin(username, password);

        assertFalse(result);
    }

    @Test
    void testGetUserByUsername() {
        String username = "user";

        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.getUserByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void testGetRole() {
        String username = "user";

        User user = new User();
        user.setUsername(username);
        user.setRole("admin");

        when(userRepository.findByUsername(username)).thenReturn(user);

        String role = userService.getRole(username);

        assertEquals("admin", role);
    }
}