package space.emdon.dangkyhocphan.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
INVALID_KEY(9000, "Uncategorized error", HttpStatus.BAD_REQUEST),
USER_NOT_EXIST(1001, "User not found", HttpStatus.NOT_FOUND),
EMAIL_EXISTED(1002, "Email exists", HttpStatus.BAD_REQUEST),
NAME_INVALID(1003, "Name is invalid", HttpStatus.BAD_REQUEST),
EMAIL_INVALID(1004, "Email is invalid", HttpStatus.BAD_REQUEST),
NUMBER_ID_INVALID(1005, "Number_ID invalid", HttpStatus.BAD_REQUEST),
PASSWORD_INVALID(1006, "Password is invalid", HttpStatus.BAD_REQUEST),
INVALID_CREDENTIAL(1007, "Invalid email or password", HttpStatus.UNAUTHORIZED),
UNAUTHORIZED(1008, "Unauthorized", HttpStatus.FORBIDDEN),
INVALID_TOKEN(1009, "Invalid token", HttpStatus.BAD_REQUEST),
UNAUTHENTICATED(1010, "Unauthenticated", HttpStatus.UNAUTHORIZED),
APPLICATION_JSON_VALUE(
	1011, "Content type must be application/json application/json", HttpStatus.BAD_REQUEST),
PHONE_INVALID(1012, "Phone is invalid", HttpStatus.BAD_REQUEST),
DOB_INVALID(
	1013, "Date of birth is invalid - must be at least {min} years old ", HttpStatus.BAD_REQUEST),
ROLE_NOT_FOUND(1014, "Role not found", HttpStatus.NOT_FOUND),
ROLE_EXISTED(1015, "Role existed", HttpStatus.BAD_REQUEST),
PERMISSION_NOT_FOUND(1016, "Permission not found", HttpStatus.NOT_FOUND),

SUBJECT_ALREADY_EXISTS(1017, "Subject already exists", HttpStatus.BAD_REQUEST),
SUBJECT_CODE_EXISTED(1018, "Subject code existed", HttpStatus.BAD_REQUEST),
SUBJECT_NAME_EXISTED(1019, "Subject name existed", HttpStatus.BAD_REQUEST),
SUBJECT_NOT_FOUND(1020, "Subject not found", HttpStatus.NOT_FOUND),
	SUBJECT_CODE_REQUIRED(1021, "Subject code is required", HttpStatus.BAD_REQUEST),
	SUBJECT_NAME_REQUIRED(1022, "Subject name is required", HttpStatus.BAD_REQUEST),
	CREDITS_INVALID(1023, "Subject credits is min = 1", HttpStatus.BAD_REQUEST),

SCHEDULE_NOT_FOUND(1025, "Schedule not found", HttpStatus.NOT_FOUND),
SCHEDULE_ALREADY_EXISTS(1026, "Schedule already exists", HttpStatus.BAD_REQUEST),
SCHEDULE_NOT_EXIST(1027, "Schedule not exist", HttpStatus.NOT_FOUND),

	SEMESTER_NAME_INVALID(1031, "Semester name is required", HttpStatus.BAD_REQUEST),
	START_DATE_INVALID(1032, "Start date is required", HttpStatus.BAD_REQUEST),
	REG_START_INVALID(1033, "End date is required", HttpStatus.BAD_REQUEST),
	REG_END_INVALID(1034, "Reg date is required", HttpStatus.BAD_REQUEST),
	SEMESTER_NOT_FOUND(1035, "Semester not found", HttpStatus.NOT_FOUND),







;

ErrorCode(int code, String message, HttpStatusCode statusCode) {
	this.code = code;
	this.message = message;
	this.statusCode = statusCode;
}

final int code;
final String message;
final HttpStatusCode statusCode;
}
