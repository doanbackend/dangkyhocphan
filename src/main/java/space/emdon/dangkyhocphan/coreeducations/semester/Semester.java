package space.emdon.dangkyhocphan.coreeducations.semester;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.BatchSize;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Semester {
@Id String name;

@Column(nullable = false)
LocalDate startDate;

@Column(nullable = false)
LocalDate endDate;

LocalDateTime registrationStartDate;
LocalDateTime registrationEndDate;

@BatchSize(size = 20)
@OneToMany(mappedBy = "semester")
Set<Sectionclass> sectionclass;
}
