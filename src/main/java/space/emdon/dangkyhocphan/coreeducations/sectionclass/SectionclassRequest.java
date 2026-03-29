package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.coreeducations.schedule.ScheduleRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionclassRequest {

String subjectCode; // Subject.code
String lecturerNumberId; // User.numberid
String semesterName; // Semester.name
int maxStudents;
Set<ScheduleRequest> schedules;
}
