package platform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import platform.backend.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
    List<User> findByRole(String role);
    boolean existsByUsernameAndRole(String username, String role);
}

