package bilingual.dto.request.auth;

import bilingual.validation.valid.EmailValidation;
import bilingual.validation.valid.PasswordValidation;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignUpRequest(
        @NotBlank(message = "Name must be provided")
        String firstName,
        @NotBlank(message = "Last name must be provided")
        String lastName,
        @Column(unique = true)
        @EmailValidation
        String email,
        @PasswordValidation
        String password
) {
}
