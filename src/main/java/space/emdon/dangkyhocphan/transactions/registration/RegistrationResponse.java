package space.emdon.dangkyhocphan.transactions.registration;


import lombok.*;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.enums.RegistrationStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationResponse {

    String id;
    String studentId;
    String studentName;
    Long sectionClassId;
    String subjectName;
    String room;
    RegistrationStatus status;
    LocalDateTime registrationDateTime;

}
