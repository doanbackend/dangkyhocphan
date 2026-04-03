package space.emdon.dangkyhocphan.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import space.emdon.dangkyhocphan.validator.PhoneValidator;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {PhoneValidator.class})
public @interface NumberidConstraint {
String message() default "{NUMBERID_INVALID}";

Class<?>[] groups() default {};

Class<? extends Payload>[] payload() default {};
}
