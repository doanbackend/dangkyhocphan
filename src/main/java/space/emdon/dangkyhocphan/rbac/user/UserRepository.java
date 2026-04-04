package space.emdon.dangkyhocphan.rbac.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

boolean existsByPhone(String phone);

@EntityGraph(attributePaths = {"roles", "roles.permissions"})
Optional<User> findByPhone(String phone);

@EntityGraph(attributePaths = {"roles", "roles.permissions"})
Optional<User> findByNumbered(String numbered);

@EntityGraph(attributePaths = {"roles", "roles.permissions"})
Optional<User> findById(String id);

Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name <> 'ADMIN'")
    Page<User> findUsers(Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'STUDENT'")
    Page<User> findStudents(Pageable pageable);

@EntityGraph(attributePaths = {"roles"})

List<User> findByRolesName(String roleName);

@Query("SELECT COUNT(u) > 0 FROM User u JOIN u.roles r WHERE r.name = :roleName")
boolean existsByRolesName(@Param("roleName") String roleName);

boolean existsByNumbered(String numbered);
}
