package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionclassRepository extends JpaRepository<Sectionclass, String> {

Optional<Sectionclass> findByName(String name);

List<Sectionclass> findAllByNameIn(List<String> names);

void deleteByName(String name);

boolean existsBySubjectCode(String subjectCode);

boolean existsBySemesterName(String semesterName);
}
