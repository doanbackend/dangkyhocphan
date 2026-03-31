package space.emdon.dangkyhocphan.rbac.user;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

boolean existsByEmail(String email);

Optional<User> findByEmail(String email);

@Query(
    """
        SELECT u FROM User u
        WHERE NOT EXISTS (
            SELECT 1 FROM u.roles r WHERE r.name = 'ADMIN'
        )
    """)
List<User> findUsers();

@Query(
    """
        SELECT DISTINCT u FROM User u
        JOIN u.roles r
        WHERE r.name = 'STUDENT'
    """)
List<User> findStudents();
boolean existsByName(String name);

boolean existsByNumbered(String numbered);

Optional<User> findByNumbered(String numbered);
}
