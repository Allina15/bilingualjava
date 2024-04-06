package bilingual.entity;

import bilingual.entity.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_generator")
    @SequenceGenerator(name = "result_generator", sequenceName = "result_seq", allocationSize = 1, initialValue = 5)
    private Long id;
    private LocalDateTime dateOfSubmission;
    private Boolean isSeen;
    private double score;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Test test;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "results", cascade = {PERSIST, REMOVE})
    private List<Answer> answer;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}



