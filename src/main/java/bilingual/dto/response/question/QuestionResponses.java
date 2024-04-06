package bilingual.dto.response.question;

import bilingual.dto.response.option.OptionResponses;
import bilingual.entity.enums.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionResponses {

    private Long questionId;
    private Long testId;
    private String title;
    private int duration;
    private QuestionType questionType;
    private String statement;
    private String passage;
    private int attempts;
    private String fileUrl;
    private Boolean enable;
    private List<OptionResponses> optionResponses;

    public QuestionResponses(Long questionId, Long testId,String title, int duration, QuestionType questionType, String statement, String passage, int attempts, String fileUrl, Boolean enable, List<OptionResponses> optionResponses) {
        this.questionId = questionId;
        this.testId = testId;
        this.title = title;
        this.duration = duration;
        this.questionType = questionType;
        this.statement = statement;
        this.passage = passage;
        this.attempts = attempts;
        this.fileUrl = fileUrl;
        this.enable = enable;
        this.optionResponses = optionResponses;
    }
}
