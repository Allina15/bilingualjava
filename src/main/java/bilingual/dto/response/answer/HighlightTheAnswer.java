package bilingual.dto.response.answer;

import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HighlightTheAnswer {

    private Long answerId;
    private Long testId;
    private String questionTitle;
    private String testTitle;
    private int duration;
    private QuestionType questionType;
    private double score;
    private String fullName;
    private String passage;
    private String statement;
    private String correctAnswer;
    private String userAnswer;
    private Status status;

    public HighlightTheAnswer(Long answerId, Long testId, int duration, QuestionType questionType, double score, String questionTitle, String correctAnswer, String statement, String passage, String userAnswer, String testTitle, String fullName, Status status) {
        this.answerId = answerId;
        this.testId = testId;
        this.duration = duration;
        this.questionType = questionType;
        this.score = score;
        this.questionTitle = questionTitle;
        this.correctAnswer = correctAnswer;
        this.statement = statement;
        this.passage = passage;
        this.userAnswer = userAnswer;
        this.testTitle = testTitle;
        this.fullName = fullName;
        this.status = status;
    }
}
