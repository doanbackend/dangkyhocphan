package space.emdon.dangkyhocphan.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import space.emdon.dangkyhocphan.validator.DobValidator;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
String message() default "{DOB_INVALID sai ngay sinh }";

int min();

Class<?>[] groups() default {};

Class<? extends Payload>[] payload() default {};
}
