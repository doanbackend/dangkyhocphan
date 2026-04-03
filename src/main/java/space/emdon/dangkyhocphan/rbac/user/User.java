package space.emdon.dangkyhocphan.rbac.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.BatchSize;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.Sectionclass;
import space.emdon.dangkyhocphan.rbac.role.Role;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
@ToString(exclude = "roles")
@EqualsAndHashCode(exclude = "roles")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
String id;

@Column(length = 10, nullable = false, updatable = false, unique = true)
String numbered;

String name;

@Column(nullable = false, unique = true)
String phone;

String password;

LocalDate dob;

@BatchSize(size = 50)
@ManyToMany(fetch = FetchType.LAZY)
Set<Role> roles;

@BatchSize(size = 50)
@OneToMany(mappedBy = "lecturer")
Set<Sectionclass> sectionclass;
}
