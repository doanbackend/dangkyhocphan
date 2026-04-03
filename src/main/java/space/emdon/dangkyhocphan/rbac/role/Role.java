package space.emdon.dangkyhocphan.rbac.role;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
import space.emdon.dangkyhocphan.rbac.permission.Permission;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(exclude = "permissions")
@EqualsAndHashCode(exclude = "permissions")
public class Role {

@Id String name;

String description;

@ManyToMany(fetch = FetchType.LAZY)
Set<Permission> permissions;
}
