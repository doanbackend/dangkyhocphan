package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionclassRepository extends JpaRepository<Sectionclass, String> {

@EntityGraph(attributePaths = {"subject", "semester", "lecturer"})
Optional<Sectionclass> findByName(String name);

List<Sectionclass> findAllByNameIn(List<String> names);

void deleteByName(String name);

boolean existsBySubjectCode(String subjectCode);

boolean existsBySemesterName(String semesterName);

@Query(
	"SELECT new space.emdon.dangkyhocphan.coreeducations.sectionclass.SectionclassJoinDTO(sc.name, sc.maxStudents, sc.currentStudents, sc.subject.code, sc.subject.name, sc.semester.name, sc.lecturer.numbered, sc.lecturer.name) FROM Sectionclass sc")
Page<SectionclassJoinDTO> findAllProjectedWithJoins(Pageable pageable);
}
