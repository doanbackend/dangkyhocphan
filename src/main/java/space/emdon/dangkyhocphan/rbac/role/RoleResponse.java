package space.emdon.dangkyhocphan.rbac.role;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;
import space.emdon.dangkyhocphan.rbac.permission.PermissionResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {

    String name;
    String description;
    Set<PermissionResponse> permissions;

}
