package space.emdon.dangkyhocphan.coreeducations.subject;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
@EntityGraph(attributePaths = {"sectionclass"})
Optional<Subject> findByName(String name);

@EntityGraph(attributePaths = {"sectionclass"})
Optional<Subject> findByCode(String code);

@EntityGraph(attributePaths = {"sectionclass"})
Page<Subject> findAll(Pageable pageable);

@Query(
	"SELECT new space.emdon.dangkyhocphan.coreeducations.subject.SubjectListDTO(s.code, s.name, s.credits) FROM Subject s")
Page<SubjectListDTO> findAllProjected(Pageable pageable);

boolean existsByName(String name);

boolean existsByCode(String code);

boolean existsByCodeAndName(String code, String name);

void deleteByCode(String code);
}
