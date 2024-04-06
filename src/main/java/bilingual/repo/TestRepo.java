package bilingual.repo;

import bilingual.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepo extends JpaRepository<Test, Long> {
    Optional<Test> getTestById(Long id);
}
