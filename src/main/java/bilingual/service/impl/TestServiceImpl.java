package bilingual.service.impl;

import bilingual.dto.request.test.TestRequest;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.dto.response.test.TestForUpdate;
import bilingual.dto.response.test.TestResponse;
import bilingual.dto.response.test.TestResponses;
import bilingual.entity.Test;
import bilingual.repo.TestRepo;
import bilingual.repo.jdbcRepo.TestDao;
import bilingual.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestDao testDao;
    private final TestRepo testRepo;

    @Override
    public SimpleResponse save(TestRequest testRequest) {
        Test test = Test.builder()
                .title(testRequest.title())
                .shortDescription(testRequest.shortDescription())
                .enable(false)
                .isPassed(false)
                .build();

        testRepo.save(test);
        log.info("Test with id: " + testRequest.title() + " is successfully saved");
        return new SimpleResponse(HttpStatus.OK,
                ("Test is successfully saved"));
    }

    @Override
    public List<TestForUpdate> deleteTestById(Long testId) {
        Test test = testRepo.getTestById(testId).orElseThrow(() -> {
            String message = "Could not found test with id: " + testId;
            log.error(message);
            return new NotFoundException(message);
        });

        testRepo.delete(test);
        log.info("Test with ID {} successfully deleted", testId);
        return testDao.getTestForUpdate();
    }

    @Override
    public TestResponse getAllQuestionByTestId(Long testId) {
        log.info("Retrieved all questions for test with ID: {}", testId);
        return testDao.getAllQuestionByTestId(testId);
    }

    @Override
    public SimpleResponse updateTestById(Long id, TestRequest testRequest) {
        Test test = testRepo.getTestById(id).orElseThrow(() -> {
            String message = "Test with id: %s not found, testId";
            log.error(message);
            return new NotFoundException(message);
        });

        if (testRequest.title() != null && !testRequest.title().isEmpty()) {
            test.setTitle(testRequest.title());
        }
        if (testRequest.shortDescription() != null && !testRequest.shortDescription().isEmpty()) {
            test.setShortDescription(testRequest.shortDescription());
        }

        testRepo.save(test);
        log.info("Test with ID {} successfully updated", id);
        return new SimpleResponse(HttpStatus.OK, "Test successfully updated");

    }

    @Override
    public List<TestForUpdate> updateTestEnable(Long id, boolean enableUpdate) {
        Test test = testRepo.getTestById(id).orElseThrow(() -> {
            String message = "Test with id: %s not found, testId";
            log.error(message);
            return new NotFoundException(message);
        });

        test.setEnable(enableUpdate);
        testRepo.save(test);
        log.info("Test with ID {} successfully updated", id);
        return testDao.getTestForUpdate();
    }

    @Override
    public List<TestResponses> getAllTest() {
        return testDao.getAllTest();
    }

    @Override
    public TestResponses getTestById(Long testId) {
        return testDao.getTestById(testId);
    }
}