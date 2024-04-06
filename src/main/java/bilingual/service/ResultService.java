package bilingual.service;

import bilingual.dto.response.result.MyResultResponse;
import bilingual.dto.response.result.ResultResponse;
import bilingual.dto.response.result.ResultResponses;
import bilingual.dto.response.test.SimpleResponse;

import java.util.List;

public interface ResultService {
    List<MyResultResponse> getAll();
    SimpleResponse deleteResult(Long id);
    SimpleResponse checkAnswer(Long answerId, double score);
    SimpleResponse sendResult(Long resultId, String link);
    ResultResponses getAllAnswerByResultId(Long resultId);
    List<ResultResponse> getAllTestResults();
}
