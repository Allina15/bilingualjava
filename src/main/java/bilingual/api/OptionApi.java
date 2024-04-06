package bilingual.api;

import bilingual.dto.request.option.OptionRequest;
import bilingual.dto.response.option.OptionsResponse;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/option")
@Tag(name = "Option API", description = "APIs for managing options")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin
public class OptionApi {

    private final OptionService optionService;

    @GetMapping
    @Operation(summary = "Get all options by question ID")
    public OptionsResponse getAllOptions(@RequestParam Long questionId){
        return optionService.getOptionByQuestionId(questionId);
    }

    @PostMapping
    @Operation(summary = "Add new option")
    public SimpleResponse addOption(@RequestParam Long questionId, @RequestBody List<OptionRequest> optionRequest){
        return optionService.addOption(questionId, optionRequest);
    }

    @DeleteMapping
    @Operation(summary = "Delete option")
    public SimpleResponse delete(@RequestParam Long optionId){
        return optionService.deleteOption(optionId);
    }
}


