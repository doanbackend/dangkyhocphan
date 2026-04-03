package space.emdon.dangkyhocphan.rbac.role;

import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
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
