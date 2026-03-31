package space.emdon.dangkyhocphan.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.emdon.dangkyhocphan.constraint.NumberedConstraint;

public class NumberedValidator implements ConstraintValidator<NumberedConstraint, String> {

@Override
public void initialize(NumberedConstraint constraintAnnotation) {}

@Override
public boolean isValid(String value, ConstraintValidatorContext context) {
	if (value == null || value.isEmpty()) {
	return true;
	}

	return value.matches("\\d+");
}
}
