package space.emdon.dangkyhocphan.rbac.role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
Optional<Role> findByName(String name);

boolean existsByPermissionsName(String permissionName);

@EntityGraph(attributePaths = {"permissions"})
List<Role> findAll();



}
