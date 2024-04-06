package bilingual.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InitiatePassword {

    @NotBlank(message = "Email must be provided")
    private String email;

}
