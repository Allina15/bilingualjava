package bilingual.dto.response.test;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestForUpdate {

    private Long id;
    private String title;
    private Boolean enable;

    public TestForUpdate(Long id, String title,  Boolean enable) {
        this.id = id;
        this.title = title;
        this.enable= enable;
    }
}
