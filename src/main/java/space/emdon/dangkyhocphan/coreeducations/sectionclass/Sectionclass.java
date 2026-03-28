package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import space.emdon.dangkyhocphan.rbac.user.User;
import space.emdon.dangkyhocphan.coreeducations.subject.Subject;

import space.emdon.dangkyhocphan.coreeducations.schedule.Schedule;
import space.emdon.dangkyhocphan.coreeducations.semester.Semester;


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
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_code")
    Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_numberid")
    User instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_name")
    Semester semester;

    int maxStudents = 100;
    int currentStudents = 0;

    @OneToMany(mappedBy = "sectionClass", cascade = CascadeType.ALL)
    Set<Schedule> schedules;

    @Version
    Long version;

}
