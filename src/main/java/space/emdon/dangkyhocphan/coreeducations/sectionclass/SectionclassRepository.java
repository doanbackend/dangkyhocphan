package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionclassRepository extends JpaRepository<Sectionclass, Long> {

@EntityGraph(attributePaths = {"subject", "instructor", "semester"})
Optional<Sectionclass> findById(Long id);

@EntityGraph(attributePaths = {"subject", "instructor", "semester"})
List<Sectionclass> findAllByIdIn(Iterable<Long> ids);

@Query(
	"SELECT COUNT(s) > 0 FROM Sectionclass s JOIN s.schedules sch "
		+ "WHERE s.semester.name = :semesterName "
		+ "AND sch.dayOfWeek = :dayOfWeek "
		+ "AND sch.startPeriod <= :endP AND sch.endPeriod >= :startP")
boolean existsConflict(
	@Param("semesterName") String semesterName,
	@Param("dayOfWeek") String dayOfWeek,
	@Param("startP") int startP,
	@Param("endP") int endP);

@Query(
	"SELECT COUNT(s) > 0 FROM Sectionclass s JOIN s.schedules sch "
		+ "WHERE s.instructor.numberid = :instructorNumberId "
		+ "AND s.semester.name = :semesterName "
		+ "AND sch.dayOfWeek = :dayOfWeek "
		+ "AND sch.startPeriod <= :endP AND sch.endPeriod >= :startP")
boolean existsInstructorConflict(
	@Param("instructorNumberId") String instructorNumberId,
	@Param("semesterName") String semesterName,
	@Param("dayOfWeek") String dayOfWeek,
	@Param("startP") int startP,
	@Param("endP") int endP);

@Query("SELECT s.currentStudents FROM Sectionclass s WHERE s.id = :sectionId")
int getCurrentStudents(@Param("sectionId") String sectionId);
}
