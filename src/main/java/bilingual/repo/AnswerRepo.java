package bilingual.repo;

import bilingual.entity.Answer;
import bilingual.entity.Option;
import bilingual.entity.Question;
import bilingual.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Long> {
    List<Answer> findByOptionsContaining(Option option);
    boolean existsByUserAndQuestion(User user, Question question);
    List<Answer> findAllByResultsId(Long resultId);
}
