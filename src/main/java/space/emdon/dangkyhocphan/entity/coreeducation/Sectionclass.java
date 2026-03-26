package space.emdon.dangkyhocphan.entity.coreeducation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.entity.rbac.User;

import java.util.Set;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


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

    @ManyToOne
    Subject subject;
    @ManyToOne
    User instructor;

    int maxStudents;
    @ManyToOne
    Semester semester;
    @OneToMany(mappedBy = "sectionClass")
    Set<Schedule> schedules;
}
