package space.emdon.dangkyhocphan.rbac.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.constraint.DobConstraint;
import space.emdon.dangkyhocphan.constraint.PhoneConstraint;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
@NotBlank(message = "NAME_REQUIRED")
@Size(min = 5, message = "NAME_INVALID")
String name;

@NotBlank(message = "PHONE_REQUIRED")
@PhoneConstraint(message = "PHONE_INVALID")
String phone;

@NotBlank(message = "PASSWORD_REQUIRED")
@Size(min = 8, message = "PASSWORD_INVALID")
String password;

@Size(min = 2, max = 2, message = "NUMBER_ID_INVALID")
@Pattern(regexp = "^[1-9][0-9]$", message = "NUMBER_ID_INVALID")
String numbered;

@NotNull(message = "DOB_REQUIRED")
@DobConstraint(min = 10, message = "DOB_INVALID")
LocalDate dob;

Set<String> roles;
Set<String> sectionclass;
}
