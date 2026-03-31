package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import jakarta.persistence.*;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.coreeducations.schedule.Schedule;
import space.emdon.dangkyhocphan.coreeducations.semester.Semester;
import space.emdon.dangkyhocphan.coreeducations.subject.Subject;
import space.emdon.dangkyhocphan.rbac.user.User;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Sectionclass {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
Long id;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "subject_code", referencedColumnName = "code")
Subject subject;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "instructor_numbered", referencedColumnName = "numbered")
User instructor;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "semester_name", referencedColumnName = "name")
Semester semester;

@Builder.Default
@Column(name = "max_students")
int maxStudents = 100;

@Builder.Default
@Column(name = "current_students")
int currentStudents = 0;

@OneToMany(mappedBy = "sectionClass", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
Set<Schedule> schedules;

@Version Long version;
}
