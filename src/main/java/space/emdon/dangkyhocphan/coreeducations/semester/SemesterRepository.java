package space.emdon.dangkyhocphan.coreeducations.semester;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {

@EntityGraph(attributePaths = {"sectionclass"})
Optional<Semester> findByName(String name);



@EntityGraph(attributePaths = {"sectionclass"})
List<Semester> findAll();

@Query(
	"SELECT new space.emdon.dangkyhocphan.coreeducations.semester.SemesterListDTO(s.name, s.startDate, s.endDate) FROM Semester s")
Page<SemesterListDTO> findAllProjected(Pageable pageable);
}
