package space.emdon.dangkyhocphan.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.emdon.dangkyhocphan.constraint.NumberidConstraint;

public class NumberidValidator implements ConstraintValidator<NumberidConstraint, String> {

    @Override
    public void initialize(NumberidConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return value.matches("\\d+");
    }
}