package bilingual.dto.request.question;

import java.util.List;

public record UpdateRequest(
        String title,
        int duration,
        String statement,
        String passage,
        int attempts,
        String correctAnswer,
        String fileUrl,
        List<OptionUpdate> optionRequest
){
}
