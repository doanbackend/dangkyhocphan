package space.emdon.dangkyhocphan.rbac.user;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String numberid;
    String email;
    String password;
    String phone;
    LocalDate dob;
    Set<RoleResponse> roles;
    Set<SectionclassResponse> Sectionclass;
}
