package bilingual.dto.request.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {

    private Long attempts;
    private String input;
    private String audioFile;
    private List<Long> optionId;
    private Long questionID;
}
