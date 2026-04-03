package space.emdon.dangkyhocphan.transactions.registration;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.enums.RegistrationStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationResponse {
String id;
String studentNumbered;
String studentName;
String sectionclassName;
String subjectName;
String room;
RegistrationStatus status;
LocalDateTime registrationDateTime;
}
