package bilingual.entity;

import bilingual.entity.enums.QuestionType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name = "question_generator", sequenceName = "question_seq", allocationSize = 1, initialValue = 28)
    private Long id;
    private String title;
    private int duration;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    private String statement;
    @Column(length = 5000)
    private String passage;
    private int attempts;
    @Column(length = 5000)
    private String correctAnswer;
    private String fileUrl;
    private Boolean enable;
    @OneToMany(mappedBy = "question", cascade = {DETACH, REMOVE})
    private List<Option> option;
    @OneToMany(mappedBy = "question", cascade = {DETACH, REMOVE})
    private List<Answer> answer;
    @ManyToOne
    private Test test;

    public boolean isEnable() {
        return true;
    }

    public Question(Long id, String title, int duration, QuestionType questionType, String statement, String passage, int attempts, String correctAnswer, String fileUrl, Boolean enable, Test test) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.questionType = questionType;
        this.statement = statement;
        this.passage = passage;
        this.attempts = attempts;
        this.correctAnswer = correctAnswer;
        this.fileUrl = fileUrl;
        this.enable = enable;
        this.test = test;
    }
}
