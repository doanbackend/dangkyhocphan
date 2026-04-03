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
INVALID_CREDENTIAL(1007, "Invalid email or password", HttpStatus.UNAUTHORIZED),
INVALID_TOKEN(1009, "Invalid token", HttpStatus.BAD_REQUEST),
UNAUTHORIZED(1008, "Unauthorized", HttpStatus.FORBIDDEN),
UNAUTHENTICATED(1010, "Unauthenticated", HttpStatus.UNAUTHORIZED),

USER_NOT_FOUND(1101, "User not found", HttpStatus.NOT_FOUND),
USER_NOT_EXIST(1102, "User not exist", HttpStatus.NOT_FOUND),
CANNOT_DELETE_ADMIN(1103, "Cannot delete admin", HttpStatus.BAD_REQUEST),
NAME_INVALID(1104, "Name is invalid", HttpStatus.BAD_REQUEST),
EMAIL_INVALID(1105, "Email is invalid", HttpStatus.BAD_REQUEST),
EMAIL_EXISTED(1106, "Email exists", HttpStatus.BAD_REQUEST),
NUMBER_ID_INVALID(1107, "Number_ID invalid", HttpStatus.BAD_REQUEST),
PASSWORD_INVALID(1108, "Password is invalid", HttpStatus.BAD_REQUEST),
APPLICATION_JSON_VALUE(
	1109, "Content type must be application/json application/json", HttpStatus.BAD_REQUEST),
PHONE_INVALID(1110, "Phone is invalid", HttpStatus.BAD_REQUEST),
PHONE_EXISTED(1112, "Phone already exists", HttpStatus.BAD_REQUEST),
DOB_INVALID(
	1111, "Date of birth is invalid - must be at least {min} years old ", HttpStatus.BAD_REQUEST),

ROLE_NOT_FOUND(1201, "Role not found", HttpStatus.NOT_FOUND),
ROLE_NOT_EXIST(1202, "Role not exist", HttpStatus.NOT_FOUND),
ROLE_EXISTED(1203, "Role already exists", HttpStatus.BAD_REQUEST),
ROLE_IN_USE(1204, "Role is being used by users", HttpStatus.BAD_REQUEST),

PERMISSION_NOT_FOUND(1301, "Permission not found", HttpStatus.NOT_FOUND),
PERMISSION_NOT_EXIST(1302, "Permission not exist", HttpStatus.NOT_FOUND),
PERMISSION_NAME_REQUIRED(1303, "Permission name is required", HttpStatus.BAD_REQUEST),
PERMISSION_EXISTED(1304, "Permission already exists", HttpStatus.BAD_REQUEST),
PERMISSION_IN_USE(1305, "Permission is being used by roles", HttpStatus.BAD_REQUEST),

SUBJECT_NOT_FOUND(1401, "Subject not found", HttpStatus.NOT_FOUND),
SUBJECT_NOT_EXISTS(1402, "Subject not exists", HttpStatus.NOT_FOUND),
SUBJECT_ALREADY_EXISTS(1403, "Subject already exists", HttpStatus.BAD_REQUEST),
SUBJECT_CODE_EXISTED(1404, "Subject code existed", HttpStatus.BAD_REQUEST),
SUBJECT_NAME_EXISTED(1405, "Subject name existed", HttpStatus.BAD_REQUEST),
SUBJECT_CODE_REQUIRED(1406, "Subject code is required", HttpStatus.BAD_REQUEST),
SUBJECT_NAME_REQUIRED(1407, "Subject name is required", HttpStatus.BAD_REQUEST),
CREDITS_INVALID(1408, "Subject credits is min = 1", HttpStatus.BAD_REQUEST),
SUBJECT_IN_USE(1409, "Subject is being used by section classes", HttpStatus.BAD_REQUEST),

SEMESTER_NOT_FOUND(1501, "Semester not found", HttpStatus.NOT_FOUND),
SEMESTER_NOT_EXIST(1502, "Semester not exist", HttpStatus.NOT_FOUND),
SEMESTER_NAME_INVALID(1503, "Semester name is invalid", HttpStatus.BAD_REQUEST),
START_DATE_INVALID(1504, "Start date is required", HttpStatus.BAD_REQUEST),
END_DATE_INVALID(1505, "End date is required", HttpStatus.BAD_REQUEST),
REG_START_INVALID(1506, "End date is required", HttpStatus.BAD_REQUEST),
REG_END_INVALID(1507, "Reg date is required", HttpStatus.BAD_REQUEST),
SEMESTER_EXISTS(1508, "Semester already exists", HttpStatus.BAD_REQUEST),
INVALID_DATE_RANGE(1509, "Start date must be before end date", HttpStatus.BAD_REQUEST),
INVALID_REGISTRATION_DATE_RANGE(
	1510, "Registration start date must be before registration end date", HttpStatus.BAD_REQUEST),
SEMESTER_IN_USE(1511, "Semester is being used by section classes", HttpStatus.BAD_REQUEST),

SCHEDULE_NOT_FOUND(1701, "Schedule not found", HttpStatus.NOT_FOUND),
SCHEDULE_NOT_EXIST(1702, "Schedule not exist", HttpStatus.NOT_FOUND),
DAY_OF_WEEK_REQUIRED(1703, "Day of week is required", HttpStatus.BAD_REQUEST),
START_PERIOD_INVALID(1704, "Start period is invalid", HttpStatus.BAD_REQUEST),
END_PERIOD_INVALID(1705, "End period is invalid", HttpStatus.BAD_REQUEST),
INVALID_SCHEDULE_PERIOD(
	1706, "Start period must be less than end period", HttpStatus.BAD_REQUEST),
SECTION_CLASS_NAME_REQUIRED(1707, "Section class name is required", HttpStatus.BAD_REQUEST),
SCHEDULE_ALREADY_EXISTS(1708, "Schedule already exists", HttpStatus.BAD_REQUEST),

SECTIONCLASS_NOT_FOUND(1601, "Section class not found", HttpStatus.NOT_FOUND),
SECTIONCLASS_NOT_EXIST(1602, "Section class not exist", HttpStatus.NOT_FOUND),
ROOM_REQUIRED(1603, "Room is required", HttpStatus.BAD_REQUEST),
SECTIONCLASS_IN_USE(1604, "Section class is being used by schedules", HttpStatus.BAD_REQUEST),

INVOICE_NOT_FOUND(1701, "Invoice not found", HttpStatus.NOT_FOUND),
INVOICE_NOT_EXIST(1702, "Invoice not exist", HttpStatus.NOT_FOUND),
INVOICE_DELETE_FORBIDDEN(
	1703, "Invoice deletion is forbidden. Use update status instead", HttpStatus.FORBIDDEN),

REGISTRATION_NOT_FOUND(1801, "Registration not found", HttpStatus.NOT_FOUND),
REGISTRATION_NOT_EXIST(1802, "Registration not exist", HttpStatus.NOT_FOUND),
STUDENT_NUMBER_ID_REQUIRED(1803, "Student number id is required", HttpStatus.BAD_REQUEST),
SECTION_CLASS_ID_REQUIRED(1804, "Section class id is required", HttpStatus.BAD_REQUEST),
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
