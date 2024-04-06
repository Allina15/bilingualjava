package bilingual.repo;

import bilingual.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    boolean existsByEmail(String email);
    Optional<UserInfo> getAuthInfosByEmail(String email);
    UserInfo getUserInfosByEmail(String email);
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByUserId(Long userId);
    Optional<UserInfo> getUserInfoByVerificationCode(String uniqueIdentifier);

}