package space.emdon.dangkyhocphan.coreeducations.semester;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class SemesterResponse {
String name;
LocalDate startDate;
LocalDate endDate;
LocalDateTime registrationStartDate;
LocalDateTime registrationEndDate;

boolean isRegistrationOpen;
}
