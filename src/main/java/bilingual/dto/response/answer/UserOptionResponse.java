package bilingual.dto.response.answer;

import lombok.Data;

@Data
public class UserOptionResponse {

    private Long optionId;
    private Long answerId;
    private int number;
    private String optionTitle;
    private String fileUrl;
    private Boolean isCorrectOption;

    public UserOptionResponse(int number, String optionTitle, String fileUrl, Boolean isCorrectOption, Long optionId, Long answerId) {
        this.number = number;
        this.optionTitle = optionTitle;
        this.fileUrl = fileUrl;
        this.isCorrectOption = isCorrectOption;
        this.optionId = optionId;
        this.answerId = answerId;
    }
}

