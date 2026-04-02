package space.emdon.dangkyhocphan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
@NotBlank(message = "PHONE_REQUIRED")
String phone;

@NotBlank(message = "PASSWORD_REQUIRED")
String password;
}
