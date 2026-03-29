package space.emdon.dangkyhocphan.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.emdon.dangkyhocphan.constraint.PhoneConstraint;

public class PhoneValidator implements ConstraintValidator<PhoneConstraint, String> {

@Override
public boolean isValid(String value, ConstraintValidatorContext context) {
	if (value == null) return false;

	return value.matches("^0\\d{9}$");
}

@Override
public void initialize(PhoneConstraint constraintAnnotation) {
	ConstraintValidator.super.initialize(constraintAnnotation);
}
}
