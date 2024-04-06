package bilingual.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;

import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_generator")
    @SequenceGenerator(name = "test_generator", sequenceName = "test_seq", allocationSize = 1, initialValue = 4)
    private Long id;
    private String title;
    private String shortDescription;
    private Boolean enable;
    private int duration;
    private Boolean isPassed;
    @OneToMany(mappedBy = "test", cascade = {DETACH, REMOVE})
    private List<Question> questions;
    @OneToMany(mappedBy = "test", cascade = {DETACH, REMOVE})
    private List<Result> result;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Test(String testTitle, String shortDescription) {
        this.title = testTitle;
        this.shortDescription = shortDescription;
    }

    public Test(Long id, String title, String shortDescription, Boolean enable, int duration) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.enable = enable;
        this.duration = duration;
    }

    public boolean isEnable() {
        return true;
    }

}



