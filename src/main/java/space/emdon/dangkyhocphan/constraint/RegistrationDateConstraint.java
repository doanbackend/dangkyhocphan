package space.emdon.dangkyhocphan.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import space.emdon.dangkyhocphan.validator.RegistrationDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegistrationDateValidator.class)
@Documented
public @interface RegistrationDateConstraint {
    String message() default "DATE_INVALID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}