package bilingual.dto.response.option;

import lombok.Data;

@Data
public class OptionResponses{

    private Long id;
    private Long questionId;
    private String optionTitle;
    private String fileUrl;

    public OptionResponses(Long id, String optionTitle, String fileUrl, Long questionId) {
        this.id = id;
        this.optionTitle = optionTitle;
        this.fileUrl = fileUrl;
        this.questionId = questionId;
    }
}