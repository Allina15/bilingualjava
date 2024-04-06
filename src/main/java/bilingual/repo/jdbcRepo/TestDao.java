package bilingual.repo.jdbcRepo;

import bilingual.dto.response.test.TestForUpdate;
import bilingual.dto.response.test.TestResponse;
import bilingual.dto.response.test.TestResponses;

import java.util.List;

public interface TestDao {
    TestResponse getAllQuestionByTestId(Long testId);
    List<TestResponses> getAllTest();
    List<TestForUpdate> getTestForUpdate();
    TestResponses getTestById(Long testId);

}