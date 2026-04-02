package space.emdon.dangkyhocphan.rbac.user;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

boolean existsByPhone(String phone);

Optional<User> findByPhone(String phone);
Optional<User> findByNumbered(String numbered);

List<User> findAll();
@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name <> 'ADMIN'")
List<User> findUsers();
@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'STUDENT'")
List<User> findStudents();

List<User> findByRolesName(String roleName);

@Query("SELECT u FROM User u WHERE NOT EXISTS (SELECT r FROM u.roles r WHERE r.name = 'ADMIN')")
List<User> findAllNonAdmin();
boolean existsByNumbered(String numbered);
boolean existsByRolesName(String roleName);
}
