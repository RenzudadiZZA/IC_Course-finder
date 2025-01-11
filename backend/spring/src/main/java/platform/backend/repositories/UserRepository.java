package platform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import platform.backend.entities.User;
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
