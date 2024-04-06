package bilingual.dto.response.answer;

import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondInNWord {

    private Long answerId;
    private Long testId;
    private String questionTitle;
    private String testTitle;
    private int duration;
    private QuestionType questionType;
    private double score;
    private String statement;
    private int questionAttempts;
    private String userAnswer;
    private int answerAttempts;
    private String fullName;
    private Status status;

}
