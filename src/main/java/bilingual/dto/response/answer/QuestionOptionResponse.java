package bilingual.dto.response.answer;

import lombok.Data;

@Data
public class QuestionOptionResponse {

    private int number;
    private String optionTitle;
    private String fileUrl;
    private Boolean isCorrectOption;
    private Long questionId;
    private Long optionId;
    public QuestionOptionResponse(int number, String optionTitle, String fileUrl, Boolean isCorrectOption, Long questionId, Long optionId) {
        this.number = number;
        this.optionTitle = optionTitle;
        this.fileUrl = fileUrl;
        this.isCorrectOption = isCorrectOption;
        this.questionId = questionId;
        this.optionId = optionId;
    }
}
