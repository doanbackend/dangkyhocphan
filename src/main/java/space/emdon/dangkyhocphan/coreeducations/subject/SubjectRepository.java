package space.emdon.dangkyhocphan.coreeducations.subject;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
@Query("SELECT s FROM Subject s WHERE s.name = :name")
Optional<Subject> findByName(@Param("name") String name);

@Query("SELECT s FROM Subject s WHERE s.code = :code")
Optional<Subject> findByCode(@Param("code") String code);

boolean existsByName(String name);
boolean existsByCode(String code);
boolean existsByCodeAndName(String code, String name);

void deleteByCode(String code);
}
