package space.emdon.dangkyhocphan.rbac.user;

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
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import space.emdon.dangkyhocphan.rbac.role.*;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.*;
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
    @Column(length = 10, nullable = false, updatable = false)
    String numberid;
    String name;
    @Column(nullable = false, unique = true)
    String email;
    String password;
    @Column(unique = true)
    String phone;
    LocalDate dob;
    @ManyToMany(fetch = FetchType.LAZY)
    Set<Role> roles;
    @OneToMany(mappedBy = "instructor")
    Set<Sectionclass> assignedClasses;


}
