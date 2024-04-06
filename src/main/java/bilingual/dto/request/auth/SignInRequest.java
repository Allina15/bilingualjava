package bilingual.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignInRequest(
        @NotBlank(message = "Email must be provided")
        String email,
        @NotBlank(message = "Password must be provided")
        String password
) {
}
