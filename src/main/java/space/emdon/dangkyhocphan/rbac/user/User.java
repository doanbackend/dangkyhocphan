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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.*;
import space.emdon.dangkyhocphan.rbac.role.*;

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

@Column(length = 10, nullable = false, updatable = false, unique = true)
String numbered;

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
