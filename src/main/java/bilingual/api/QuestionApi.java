package bilingual.api;

import bilingual.dto.request.question.QuestionRequest;
import bilingual.dto.request.question.UpdateRequest;
import bilingual.dto.response.question.QuestionResponses;
import bilingual.dto.response.question.QuestionsResponse;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.dto.response.test.TestResponse;
import bilingual.entity.enums.QuestionType;
import bilingual.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
@Tag(name = "Question API", description = "APIs for managing questions")
@CrossOrigin
public class QuestionApi {

    private final QuestionService questionService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "Create questions")
    public SimpleResponse createQuestion(
            @RequestParam Long testId,
            @RequestParam QuestionType questionType,
            @RequestBody QuestionRequest questionRequest) {

        return questionService.createQuestion(testId, questionType, questionRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping
    @Operation(summary = "Update question")
    public SimpleResponse update(@RequestParam Long id, @RequestBody UpdateRequest request){
        return questionService.updateQuestion(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/IsEnable")
    @Operation(summary = "Update question is enable")
    public TestResponse updateQuestionBoolean(Long questionId, Boolean isEnable){
        return questionService.updateQuestionBoolean(questionId, isEnable);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    @Operation(summary = "Method to get all questions to pass test")
    public List<QuestionResponses> getAllQuestionToPassTest(@RequestParam Long testId) throws BadRequestException {
        return questionService.getAllQuestionToPassTest(testId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getById")
    @Operation(summary = "Get question by id")
    public QuestionsResponse getQuestionById(@RequestParam Long id){
        return questionService.getQuestionById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    @Operation(summary = "Delete question")
    public TestResponse deleteQuestion(@RequestParam Long questionId){
        return questionService.deleteQuestion(questionId);
    }
}