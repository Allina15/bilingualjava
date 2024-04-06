package bilingual.dto.response.answer;

import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOptionResponse {

    private Long answerId;
    private Long testId;
    private Long questionId;
    private String questionTitle;
    private String testTitle;
    private int duration;
    private QuestionType questionType;
    private double score;
    private String fullName;
    private String passage;
    private Status status;
    private List<UserOptionResponse> userOptionResponses;
    private List<QuestionOptionResponse> questionOptionResponses;
}
