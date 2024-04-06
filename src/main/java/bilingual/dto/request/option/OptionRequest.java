package bilingual.dto.request.option;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OptionRequest {

        private String optionTitle;
        private String fileUrl;
        private Boolean isCorrectOption;

        public OptionRequest(String optionTitle, String fileUrl, Boolean isCorrectOption) {
                this.optionTitle = optionTitle;
                this.fileUrl = fileUrl;
                this.isCorrectOption = isCorrectOption;
        }
}
