package bilingual.dto.response.question;

import bilingual.dto.response.answer.QuestionOptionResponse;
import bilingual.entity.enums.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionsResponse {
    private Long id;
    private String title;
    private int duration;
    private QuestionType questionType;
    private String statement;
    private String passage;
    private int attempts;
    private String correctAnswer;
    private String fileUrl;
    private Boolean enable;
    private List<QuestionOptionResponse> questionOptionResponses;

    public QuestionsResponse(Long id, String title, int duration, QuestionType questionType, String statement, String passage, int attempts, String correctAnswer, String fileUrl, Boolean enable, List<QuestionOptionResponse> questionOptionResponses) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.questionType = questionType;
        this.statement = statement;
        this.passage = passage;
        this.attempts = attempts;
        this.correctAnswer = correctAnswer;
        this.fileUrl = fileUrl;
        this.enable = enable;
        this.questionOptionResponses = questionOptionResponses;
    }
}