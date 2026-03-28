package space.emdon.dangkyhocphan.coreeducations.semester;

import jakarta.persistence.*;
import space.emdon.dangkyhocphan.constraint.RegistrationDateConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegistrationDateConstraint
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Semester {
    @Id
    @Column(nullable = false)
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
