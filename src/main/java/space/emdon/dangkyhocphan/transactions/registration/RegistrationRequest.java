package space.emdon.dangkyhocphan.transactions.registration;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {

@NotBlank(message = "STUDENT_NUMBER_ID_REQUIRED")
String studentNumberId;

@NotBlank(message = "SECTION_CLASS_NAME_REQUIRED")
String sectionclassName;
}
