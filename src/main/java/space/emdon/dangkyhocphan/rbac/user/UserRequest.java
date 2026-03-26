package space.emdon.dangkyhocphan.rbac.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @NotBlank(message = "NAME_REQUIRED")
    @Size(min = 5, message = "NAME_INCALID")
    String username;
    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    String email;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    @NumberIDConstraint(message = "NUMBER_ID_INVALID")
    String numberid;

    @PhoneConstraint(message = "PHONE_INVALID")
    String phone;

    @NotNull(message = "DOB_REQUIRED")
    @DobConstraint(min = 10, message = "DOB_INVALID")
    LocalDate dob;

    Set<String> roles;
    Set<String> assignedClasses;



}
