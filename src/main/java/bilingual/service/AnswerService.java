package bilingual.service;

import bilingual.dto.request.answer.AnswerRequest;
import bilingual.dto.response.test.SimpleResponse;

import java.util.List;

public interface AnswerService {
    SimpleResponse saveUserAnswer(List<AnswerRequest> answerRequest);
    Object retrieveAnswerByQuestionType(Long answerId);

}

