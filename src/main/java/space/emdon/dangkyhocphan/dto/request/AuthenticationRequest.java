package space.emdon.dangkyhocphan.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @jakarta.validation.constraints.NotBlank(message = "EMAIL_REQUIRED")
    @jakarta.validation.constraints.Email(message = "EMAIL_INVALID")
    String email;

    @jakarta.validation.constraints.NotBlank(message = "PASSWORD_REQUIRED")
    String password;
}