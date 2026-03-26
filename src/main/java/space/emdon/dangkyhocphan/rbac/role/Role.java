package space.emdon.dangkyhocphan.rbac.role;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;
import space.emdon.dangkyhocphan.rbac.permission.Permission;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@ToString(exclude = "permissions")
@EqualsAndHashCode(exclude = "permissions")
public class Role {

@Id String name;

String description;

@ManyToMany(fetch = FetchType.LAZY)
Set<Permission> permissions;
}
