package space.emdon.dangkyhocphan.coreeducations.schedule;

import java.time.DayOfWeek;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleResponse {
String id;
DayOfWeek dayOfWeek;
int startPeriod;
int endPeriod;
String room;
String subjectName;
String sectionclassId;
}
