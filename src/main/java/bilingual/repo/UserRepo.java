package bilingual.repo;

import bilingual.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> getUserByUserInfoEmail(String email);
    User findUserByUserInfoEmail(String email);
    boolean existsByUserInfoEmail(String email);

}
