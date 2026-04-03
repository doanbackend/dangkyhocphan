package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.BatchSize;
import space.emdon.dangkyhocphan.coreeducations.schedule.Schedule;
import space.emdon.dangkyhocphan.coreeducations.semester.Semester;
import space.emdon.dangkyhocphan.coreeducations.subject.Subject;
import space.emdon.dangkyhocphan.rbac.user.User;
import lombok.AccessLevel;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sectionclass {
@Id String name;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "subject_code", referencedColumnName = "code")
Subject subject;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "lecturer_numbered", referencedColumnName = "numbered")
User lecturer;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "semester_name", referencedColumnName = "name")
Semester semester;

@Builder.Default
@Column(name = "max_students")
int maxStudents = 200;

@Builder.Default
@Column(name = "current_students")
int currentStudents = 0;

@BatchSize(size = 50)
@OneToMany(mappedBy = "sectionclass", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
Set<Schedule> schedules;

@Version Long version;
}
