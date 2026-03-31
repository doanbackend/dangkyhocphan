package space.emdon.dangkyhocphan.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import space.emdon.dangkyhocphan.validator.NumberedValidator;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {NumberedValidator.class})
public @interface NumberedConstraint {

String message() default "NUMBERED_INVALID";

Class<?>[] groups() default {};

Class<? extends Payload>[] payload() default {};
}
