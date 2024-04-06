package bilingual.repo;

import bilingual.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepo extends JpaRepository<Option, Long> {
    List<Option> getAllByQuestionId(Long questionId);

}
