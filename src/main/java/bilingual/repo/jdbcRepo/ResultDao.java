package bilingual.repo.jdbcRepo;

import bilingual.dto.response.result.MyResultResponse;
import bilingual.dto.response.result.ResultResponse;
import bilingual.dto.response.result.ResultResponses;

import java.util.List;

public interface ResultDao {
    List<MyResultResponse> getAll(Long userId);
    ResultResponses getAllAnswerByResultId(Long resultId);
    List<ResultResponse> getAllTestResults();
}
