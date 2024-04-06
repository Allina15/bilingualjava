package bilingual.api;

import bilingual.dto.response.result.MyResultResponse;
import bilingual.dto.response.result.ResultResponse;
import bilingual.dto.response.result.ResultResponses;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/result")
@Tag(name = "Result API", description = "APIs for managing results")
@CrossOrigin
public class ResultApi {

    private final ResultService resultService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "Method to evaluating user answers")
    public SimpleResponse checkAnswer(@RequestParam Long answerId,
                                      @RequestBody double score) {
        return resultService.checkAnswer(answerId, score);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @Operation(summary = "Method to get all answers by result id")
    public ResultResponses getAllAnswer(@RequestParam Long resultId){
        return resultService.getAllAnswerByResultId(resultId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    @Operation(summary = "Method to get all submitted results")
    public List<ResultResponse> getAllTestResults(){
        return resultService.getAllTestResults();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getResult")
    @Operation(summary = "Get my results")
    public List<MyResultResponse> getMyResults(){
        return resultService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @DeleteMapping
    @Operation(summary = "Delete result")
    public SimpleResponse delete(@RequestParam Long resultId){
        return resultService.deleteResult(resultId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{resultId}")
    @Operation(summary = "Sent result")
    public SimpleResponse sentResult(@PathVariable Long resultId, @RequestParam String link){
        return resultService.sendResult(resultId, link);
    }
}