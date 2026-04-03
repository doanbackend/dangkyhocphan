package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionclassJoinDTO {
private String name;
private int maxStudents;
private int currentStudents;
private String subjectCode;
private String subjectName;
private String semesterName;
private String lecturerNumbered;
private String lecturerName;
}
