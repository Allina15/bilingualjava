package bilingual.dto.request.question;

import bilingual.dto.request.option.OptionRequest;

import java.util.List;

public record QuestionRequest(
        String title,
        int duration,
        String statement,
        String passage,
        int attempts,
        String correctAnswer,
        String fileUrl,
        List<OptionRequest> option
) {
}