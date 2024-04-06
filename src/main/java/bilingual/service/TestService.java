package bilingual.service;

import bilingual.dto.request.test.TestRequest;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.dto.response.test.TestForUpdate;
import bilingual.dto.response.test.TestResponse;
import bilingual.dto.response.test.TestResponses;

import java.util.List;

public interface TestService {

    SimpleResponse save(TestRequest testRequest);
    List<TestForUpdate> updateTestEnable(Long id, boolean enableUpdate);
    TestResponse getAllQuestionByTestId(Long testId);
    List<TestForUpdate> deleteTestById(Long testId);
    SimpleResponse updateTestById(Long id, TestRequest testRequest);
    List<TestResponses> getAllTest();
    TestResponses getTestById(Long testId);

}