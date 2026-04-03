package space.emdon.dangkyhocphan.rbac.user;

import java.time.LocalDate;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import space.emdon.dangkyhocphan.coreeducations.sectionclass.SectionclassResponse;
import space.emdon.dangkyhocphan.rbac.role.RoleResponse;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
String id;
String name;
String numbered;
String password;
String phone;
LocalDate dob;
Set<RoleResponse> roles;
Set<SectionclassResponse> sectionclass;
}
