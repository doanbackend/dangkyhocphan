package space.emdon.dangkyhocphan.entity.rbac;

import java.time.LocalDate;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.entity.coreeducation.Sectionclass;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "users")
@ToString(exclude = "roles")
@EqualsAndHashCode(exclude = "roles")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(unique = true)
    String numberid;
    String username;
    @Column(nullable = false, unique = true)
    String email;
    String password;
    LocalDate dob;
    @ManyToMany(fetch = FetchType.LAZY)
    Set<Role> roles;
    @OneToMany(mappedBy = "instructor")
    Set<Sectionclass> assignedClasses;


}
