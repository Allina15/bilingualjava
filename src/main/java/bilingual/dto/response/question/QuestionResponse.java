package bilingual.dto.response.question;

import bilingual.entity.enums.QuestionType;
import lombok.Data;

@Data
public class QuestionResponse {

    private Long id;
    private String title;
    private int duration;
    private QuestionType questionType;
    private Boolean enable;
    public QuestionResponse(Long id, String title, int duration, QuestionType questionType, boolean enable) {
        this.id=id;
        this.title=title;
        this.duration=duration;
        this.questionType=questionType;
        this.enable=enable;
    }
}