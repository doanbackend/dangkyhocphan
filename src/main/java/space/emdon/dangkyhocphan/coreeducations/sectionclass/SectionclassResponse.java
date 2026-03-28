package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;
import space.emdon.dangkyhocphan.coreeducations.schedule.ScheduleResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionclassResponse {
    String id;
    Long subjectId;
    String subjectName;
    String instructorId;
    String instructorName;
    Long semesterId;
    String semesterName;
    int maxStudents;
    int currentStudents;
    Set<ScheduleResponse> schedules;
}
