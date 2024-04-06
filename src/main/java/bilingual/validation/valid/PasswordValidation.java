package bilingual.validation.valid;

import bilingual.validation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface PasswordValidation {

    String message() default "Password must contain at least 1 uppercase letter and at least one number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

