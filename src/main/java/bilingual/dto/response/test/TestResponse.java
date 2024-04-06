package bilingual.dto.response.test;

import bilingual.dto.response.question.QuestionResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TestResponse {

    private Long id;
    private String title;
    private String shortDescription;
    private int duration;
    private List<QuestionResponse> question;
    public TestResponse(Long id, String title, String shortDescription, int duration, List<QuestionResponse> question) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.duration = duration;
        this.question = question;
    }
}