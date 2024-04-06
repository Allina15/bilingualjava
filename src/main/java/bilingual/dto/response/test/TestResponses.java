package bilingual.dto.response.test;

import lombok.Data;

@Data
public class TestResponses {

    private Long id;
    private String title;
    private Boolean enable;
    private String shortDescription;
    private Boolean isPassed;
    private int duration;

    public TestResponses(Long id, String title, Boolean enable, String shortDescription, Boolean isPassed, int duration) {
        this.id = id;
        this.title = title;
        this.enable = enable;
        this.shortDescription = shortDescription;
        this.isPassed = isPassed;
        this.duration = duration;
    }
}
