package bilingual.dto.response.answer;

import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerResponses {

    private Status status;
    private double score;
    private QuestionType questionType;
    private Boolean isChecked;
    private int number;
    private Long userId;
    private Long answerId;
    private Long testId;

}