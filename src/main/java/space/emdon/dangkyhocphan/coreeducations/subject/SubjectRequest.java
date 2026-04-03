package space.emdon.dangkyhocphan.coreeducations.subject;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SubjectRequest {
@NotBlank(message = "SUBJECT_CODE_REQUIRED")
String code;

@NotBlank(message = "SUBJECT_NAME_REQUIRED")
String name;

@Min(value = 1, message = "CREDITS_INVALID")
int credits;
}
