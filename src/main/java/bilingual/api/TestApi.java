package bilingual.api;

import bilingual.dto.request.test.TestRequest;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.dto.response.test.TestForUpdate;
import bilingual.dto.response.test.TestResponse;
import bilingual.dto.response.test.TestResponses;
import bilingual.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Tag(name = "Test API", description = "APIs for managing tests")
@CrossOrigin
public class TestApi {

    private final TestService testService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "Create test")
    public SimpleResponse createTest(@RequestBody @Valid TestRequest testRequest) {
        return testService.save(testRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    @Operation(summary = "Get all questions by testID")
    public TestResponse getAllQuestionByTestId(@RequestParam Long testId) {
        return testService.getAllQuestionByTestId(testId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    @Operation(summary = "Delete test by testID")
    public List<TestForUpdate> deleteTestById(@RequestParam Long testId) {
        return testService.deleteTestById(testId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping
    @Operation(summary = "Update test by testID")
    public SimpleResponse updateTestById(@RequestParam Long id,
                                         @RequestBody @Valid TestRequest testRequest) {
        return testService.updateTestById(id, testRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/update")
    @Operation(summary = "Update test enable by testID")
    public List<TestForUpdate> updateTestEnable(@RequestParam Long testId,
                                                @RequestParam  Boolean enable) {
        return testService.updateTestEnable(testId, enable);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all test")
    public List<TestResponses> getAllTest() {
        return testService.getAllTest();
    }

    @GetMapping("/getTestById")
    @Operation(summary = "Get test by test ID")
    public TestResponses getAllTest(@RequestParam Long testId) {
        return testService.getTestById(testId);
    }
}