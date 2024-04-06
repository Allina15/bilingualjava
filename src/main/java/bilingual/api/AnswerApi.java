package bilingual.api;

import bilingual.dto.request.answer.AnswerRequest;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
@Tag(name = "Answer API", description = "APIs for managing answers")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
@CrossOrigin
public class AnswerApi {

    private final AnswerService answerService;

    @PostMapping
    @Operation(summary = "Save users test answers input to database")
    public SimpleResponse saveAnswer(@RequestBody List<AnswerRequest> answerRequest){
        return  answerService.saveUserAnswer(answerRequest);
    }

    @GetMapping
    @Operation(summary = "Method to get each user answer by ID")
    public Object getAnswerByAnswerId(@RequestParam Long answerId){
        return answerService.retrieveAnswerByQuestionType(answerId);
    }
}

