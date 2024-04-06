package bilingual.dto.response.result;

import bilingual.entity.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ResultResponse {

    private String userFullName;
    private LocalDateTime dateOfSubmission;
    private LocalDate submissionDate;
    private LocalTime submissionTime;
    private String testName;
    private Boolean checked;
    private Status status;
    private Integer score;
    private Long userId;
    private Long testId;
    private Long resultId;
    private int number;
}