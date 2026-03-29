package space.emdon.dangkyhocphan.coreeducations.semester;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SemesterResponse {
Long id;
String name;
LocalDate startDate;
LocalDate endDate;
LocalDateTime registrationStartDate;
LocalDateTime registrationEndDate;

boolean isRegistrationOpen;
}
