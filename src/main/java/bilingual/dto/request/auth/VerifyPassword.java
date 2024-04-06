package bilingual.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyPassword {

    @NotBlank(message = "Verification code must be provided")
    private String uniqueIdentifier;
}
