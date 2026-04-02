package space.emdon.dangkyhocphan.coreeducations.semester;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.constraint.RegistrationDateConstraint;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Semester {
@Id
String name;

@Column(nullable = false)
LocalDate startDate;

@Column(nullable = false)
LocalDate endDate;

LocalDateTime registrationStartDate;
LocalDateTime registrationEndDate;

@OneToMany(mappedBy = "semester")
Set<Sectionclass> sectionClasses;
}
