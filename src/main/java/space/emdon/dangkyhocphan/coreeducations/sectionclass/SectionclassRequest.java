package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;
import space.emdon.dangkyhocphan.coreeducations.schedule.ScheduleRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionclassRequest {

    Long subjectId;
    String instructorId;
    Long semesterId;
    int maxStudents;
    Set<ScheduleRequest> schedules;

}
