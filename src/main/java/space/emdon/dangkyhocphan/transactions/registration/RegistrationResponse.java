package space.emdon.dangkyhocphan.transactions.registration;
import java.time.LocalDateTime;
import lombok.*;
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
String sectionclassId;
String subjectName;
String room;
RegistrationStatus status;
LocalDateTime registrationDateTime;
}
