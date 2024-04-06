package bilingual.repo.jdbcRepo;

import bilingual.dto.response.option.OptionsResponse;

public interface OptionDao {
    OptionsResponse getOptionByQuestionId(Long questionId);
}
