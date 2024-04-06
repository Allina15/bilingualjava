package bilingual.dto.response.answer;

import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeWhatYouHear {

    private Long answerId;
    private String questionTitle;
    private String testTitle;
    private int duration;
    private QuestionType questionType;
    private double score;
    private String fileUrl;
    private int questionAttempts;
    private String correctAnswer;
    private String userAnswer;
    private int answerAttempts;
    private String fullName;
    private Status status;

}
