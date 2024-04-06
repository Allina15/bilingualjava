package bilingual.dto.response.option;

import bilingual.dto.response.answer.QuestionOptionResponse;
import bilingual.entity.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OptionsResponse {

    private String questionTitle;
    private QuestionType questionType;
    private int duration;
    private List<QuestionOptionResponse> optionResponses;
}
