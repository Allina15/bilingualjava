package bilingual.repo;

import bilingual.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultRepo extends JpaRepository<Result, Long> {
    boolean existsByUserIdAndTestId(Long userId, Long testId);
    Optional<Result> findResultByUserIdAndTestId(Long userId, Long testId);

}
