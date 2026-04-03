package space.emdon.dangkyhocphan.coreeducations.subject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
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
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Subject {
@Id
@Column(unique = true, nullable = false)
String code;

@Column(nullable = false)
String name;

int credits;

@BatchSize(size = 20)
@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
Set<Sectionclass> sectionclass;
}
