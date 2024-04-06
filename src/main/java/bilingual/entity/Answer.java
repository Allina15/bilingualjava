package bilingual.entity;

import bilingual.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REFRESH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_generator")
    @SequenceGenerator(name = "answer_generator", sequenceName = "answer_seq", allocationSize = 1, initialValue = 37)
    private Long id;
    private double score;
    private Long attempts;
    @Column(length = 5000)
    private String data;
    private String audioFile;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Boolean isChecked;
    private LocalDateTime dateOfSubmission;
    @ManyToOne(cascade = {REFRESH, MERGE, PERSIST, DETACH})
    private Question question;
    @ManyToOne
    private Result results;
    @ManyToOne
    private User user;
    @ManyToMany(cascade = {REFRESH, MERGE, PERSIST, DETACH})
    private List<Option> options;

}