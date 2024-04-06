package bilingual.dto.response.answer;

import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import lombok.Data;

@Data
public class RecordSayingStatement {

    private Long answerId;
    private String testTitle;
    private String questionTitle;
    private int duration;
    private QuestionType questionType;
    private double score;
    private String correctAnswer;
    private String audioFile;
    private String fullName;
    private String statement;
    private Status status;

    public RecordSayingStatement(Long answerId, int duration, QuestionType questionType, double score, String fileUrl, String correctAnswer, String testTitle, String questionTitle, String fullName, String statement, Status status) {
        this.answerId = answerId;
        this.testTitle = testTitle;
        this.questionTitle = questionTitle;
        this.duration = duration;
        this.questionType = questionType;
        this.score = score;
        this.audioFile = fileUrl;
        this.correctAnswer = correctAnswer;
        this.fullName = fullName;
        this.statement = statement;
        this.status = status;
    }
}