package space.emdon.dangkyhocphan.rbac.user;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.constraint.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
@NotBlank(message = "NAME_REQUIRED")
@Size(min = 5, message = "NAME_INVALID")
String name;

@NotBlank(message = "EMAIL_REQUIRED")
@Email(message = "EMAIL_INVALID")
String email;

@NotBlank(message = "PASSWORD_REQUIRED")
@Size(min = 8, message = "PASSWORD_INVALID")
String password;

@Size(min = 2, max = 2, message = "NUMBER_ID_INVALID")
@Pattern(regexp = "^[1-9][0-9]$", message = "NUMBER_ID_INVALID")
String numbered;

@PhoneConstraint(message = "PHONE_INVALID")
String phone;

@NotNull(message = "DOB_REQUIRED")
@DobConstraint(min = 10, message = "DOB_INVALID")
LocalDate dob;

Set<String> roles;
Set<String> assignedClasses;
}
