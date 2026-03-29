package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;
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
String lecturerId;
String lecturerName;
String semesterId;
String semesterName;
int maxStudents;
int currentStudents;
Set<ScheduleResponse> schedules;
}
