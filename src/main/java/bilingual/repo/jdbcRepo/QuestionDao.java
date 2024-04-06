package bilingual.repo.jdbcRepo;

import bilingual.dto.response.question.QuestionResponses;
import bilingual.dto.response.question.QuestionsResponse;

import java.util.List;

public interface QuestionDao {
    QuestionsResponse getQuestionById(Long questionId);
    List<QuestionResponses> getAllQuestionToPassTest(Long testId);
}
