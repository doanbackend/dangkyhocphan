package space.emdon.dangkyhocphan.rbac.role;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
// Standard - Find by name
Optional<Role> findByName(String name);

// Standard - Check if role has permission
boolean existsByPermissionsName(String permissionName);

// Standard - Find all
List<Role> findAll();
}
