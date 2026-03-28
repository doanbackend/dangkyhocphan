package space.emdon.dangkyhocphan.coreeducations.schedule;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.DayOfWeek;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleResponse {
    Long id;
    DayOfWeek dayOfWeek;
    int startPeriod;
    int endPeriod;
    String room;
    String subjectName;
    String sectionClassId;
}
