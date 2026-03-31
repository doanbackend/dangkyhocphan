package space.emdon.dangkyhocphan.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.emdon.dangkyhocphan.constraint.RegistrationDateConstraint;
import space.emdon.dangkyhocphan.coreeducations.semester.Semester;
import space.emdon.dangkyhocphan.coreeducations.semester.SemesterRequest;

public class RegistrationDateValidator
	implements ConstraintValidator<RegistrationDateConstraint, SemesterRequest> {

@Override
public boolean isValid(SemesterRequest s, ConstraintValidatorContext context) {

	if (s == null) return true;

	if (s.getStartDate() != null && s.getEndDate() != null) {
	if (!s.getStartDate().isBefore(s.getEndDate())) {
		return buildError(context, "END_DATE_BEFORE_START_DATE");
	}
	}

	if (s.getRegistrationStartDate() != null && s.getRegistrationEndDate() != null) {
	if (!s.getRegistrationStartDate().isBefore(s.getRegistrationEndDate())) {
		return buildError(context, "REG_END_BEFORE_REG_START");
	}
		if (s.getStartDate() != null) {
	if (s.getRegistrationEndDate().toLocalDate().isAfter(s.getStartDate())) {
		return buildError(context, "REG_END_AFTER_START_DATE");
	}
	}
	}

	return true;
}

private boolean buildError(ConstraintValidatorContext context, String message) {
	context.disableDefaultConstraintViolation();
	context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
	return false;
}
}

