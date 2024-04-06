package bilingual.service;

import bilingual.dto.request.question.QuestionRequest;
import bilingual.dto.request.question.UpdateRequest;
import bilingual.dto.response.question.QuestionResponses;
import bilingual.dto.response.question.QuestionsResponse;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.dto.response.test.TestResponse;
import bilingual.entity.enums.QuestionType;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface QuestionService {
    SimpleResponse createQuestion(Long testId, QuestionType questionType, QuestionRequest questionRequest);
    TestResponse updateQuestionBoolean(Long questionId, Boolean isEnable);
    QuestionsResponse getQuestionById(Long questionId);
    SimpleResponse updateQuestion(Long questionId, UpdateRequest questionRequest);
    List<QuestionResponses> getAllQuestionToPassTest(Long testId) throws BadRequestException;
    TestResponse deleteQuestion(Long questionId);
}
