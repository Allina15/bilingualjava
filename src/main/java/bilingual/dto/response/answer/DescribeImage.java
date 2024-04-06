package bilingual.dto.response.answer;

import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import lombok.Data;

@Data
public class DescribeImage {

    private Long answerId;
    private Long testId;
    private String questionTitle;
    private String testTitle;
    private int duration;
    private QuestionType questionType;
    private double score;
    private String fileUrl;
    private String fullName;
    private String correctAnswer;
    private String userAnswer;
    private Status status;

    public DescribeImage(long answerId, long testId, int duration, QuestionType questionType, double score, String fileUrl, String questionTitle, String correctAnswer, String userAnswer, String testTitle, String fullName, Status status) {
        this.answerId = answerId;
        this.testId = testId;
        this.duration = duration;
        this.questionType = questionType;
        this.score = score;
        this.fileUrl = fileUrl;
        this.questionTitle = questionTitle;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
        this.testTitle = testTitle;
        this.fullName = fullName;
        this.status = status;
    }
}
