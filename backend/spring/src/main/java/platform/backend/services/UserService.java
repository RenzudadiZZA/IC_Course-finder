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

    // 管理员注册
    public User registerAdmin(String staffId, String password) throws Exception {
        // 检查 staffID 是否合法
        if (!isValidStaffId(staffId)) {
            throw new Exception("Invalid staff ID. Registration failed.");
        }

        // 检查 staffID 是否已存在（作为用户名）
        if (userRepository.existsByUsername(staffId)) {
            throw new Exception("Staff ID already exists.");
        }

        // 创建管理员用户
        User admin = new User();
        admin.setUsername(staffId); // 将 staffID 存储到 username
        admin.setPassword(password); // 密码（注意：需加密处理）
        admin.setRole("admin");

        // 保存管理员到数据库
        return userRepository.save(admin);
    }

    // 验证 staffID 合法性
    private boolean isValidStaffId(String staffId) {
        // 定义合法 staffID 的规则，这里仅支持 "1111" 作为合法 ID
        return "1111".equals(staffId);
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
