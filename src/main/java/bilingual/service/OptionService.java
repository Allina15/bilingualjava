package bilingual.service;

import bilingual.dto.request.option.OptionRequest;
import bilingual.dto.response.option.OptionsResponse;
import bilingual.dto.response.test.SimpleResponse;

import java.util.List;

public interface OptionService {
    OptionsResponse getOptionByQuestionId(Long questionId);
    SimpleResponse deleteOption(Long optionId);
    SimpleResponse addOption(Long questionId, List<OptionRequest> optionRequest);
}
