package bilingual.dto.request.auth;

import bilingual.validation.valid.PasswordValidation;
import lombok.Data;

@Data
public class ForgotPassword {

    @PasswordValidation
    private String newPassword;
    private String confirmPassword;
}
