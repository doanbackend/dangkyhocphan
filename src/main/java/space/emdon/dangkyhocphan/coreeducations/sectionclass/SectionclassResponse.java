package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.coreeducations.schedule.ScheduleResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionclassResponse {
String name;
String subjectCode;
String subjectName;
String lecturerNumbered;
String lecturerName;
String semesterName;
int maxStudents;
int currentStudents;
Set<ScheduleResponse> schedules;
}
