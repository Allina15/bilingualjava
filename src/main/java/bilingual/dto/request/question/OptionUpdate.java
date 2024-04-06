package bilingual.dto.request.question;

import lombok.Data;

@Data
public class OptionUpdate {

    private Long id;
    private String optionTitle;
    private String fileUrl;
    private Boolean isCorrectOption;

}
