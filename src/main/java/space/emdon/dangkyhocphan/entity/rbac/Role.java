package space.emdon.dangkyhocphan.entity.rbac;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
