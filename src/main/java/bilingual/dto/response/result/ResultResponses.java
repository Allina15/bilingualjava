package bilingual.dto.response.result;

import bilingual.dto.response.answer.AnswerResponses;
import bilingual.entity.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ResultResponses {

    private LocalDateTime dateOfSubmission;
    private LocalDate submissionDate;
    private LocalTime submissionTime;
    private String fullName;
    private String testName;
    private double finalScore;
    private Status status;
    private List<AnswerResponses> answerResponses;

    public ResultResponses(String fullName, LocalDateTime dateOfSubmission, String testName, double finalScore, Status status, List<AnswerResponses> answerResponses) {
        this.fullName = fullName;
        this.dateOfSubmission = dateOfSubmission;
        this.submissionDate = dateOfSubmission.toLocalDate();
        this.submissionTime = dateOfSubmission.toLocalTime();
        this.testName = testName;
        this.finalScore = finalScore;
        this.status = status;
        this.answerResponses = answerResponses;
    }
}