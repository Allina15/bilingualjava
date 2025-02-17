package bilingual.validation.valid;

import bilingual.validation.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface EmailValidation {

    String message() default "Email is invalid must ended with '@gmail.com";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
