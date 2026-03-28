package space.emdon.dangkyhocphan.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import space.emdon.dangkyhocphan.validator.NumberidValidator;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {NumberidValidator.class}) 
public @interface NumberidConstraint {
    
    String message() default "Mã số không hợp lệ, chỉ được chứa các ký tự số";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}