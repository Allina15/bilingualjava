package bilingual.dto.response.result;

import bilingual.entity.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class MyResultResponse {

    private Long resultId;
    private LocalDateTime dateOfSubmission;
    private LocalDate submissionDate;
    private LocalTime submissionTime;
    private String testName;
    private Status resultStatus;
    private double score;

    public MyResultResponse(Long resultId, LocalDateTime dateOfSubmission, String testName, Status resultStatus, double score) {
        this.resultId = resultId;
        this.dateOfSubmission = dateOfSubmission;
        this.submissionDate = dateOfSubmission.toLocalDate();
        this.submissionTime = dateOfSubmission.toLocalTime();
        this.testName = testName;
        this.resultStatus = resultStatus;
        this.score = score;
    }
}
