package space.emdon.dangkyhocphan.coreeducations.schedule;

import jakarta.validation.constraints.*;
import java.time.DayOfWeek;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleRequest {

@NotNull(message = "ID_REQUIRED")
Long id;

@NotNull(message = "DAY_OF_WEEK_REQUIRED")
DayOfWeek dayOfWeek;

@Min(value = 1, message = "START_PERIOD_INVALID")
int startPeriod;

@Max(value = 15, message = "END_PERIOD_INVALID")
int endPeriod;

@NotBlank(message = "ROOM_REQUIRED")
String room;

String sectionclassName;
}
