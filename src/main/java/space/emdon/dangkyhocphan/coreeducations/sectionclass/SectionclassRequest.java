package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.coreeducations.schedule.ScheduleRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionclassRequest {
String name;
String subjectCode;
String lecturerNumbered;
String semesterName;
int maxStudents;
Set<ScheduleRequest> schedules;
}
