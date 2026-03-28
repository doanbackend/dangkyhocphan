package space.emdon.dangkyhocphan.coreeducations.sectionclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionclassRepository extends JpaRepository<Sectionclass, String> {

    @Query("SELECT COUNT(s) > 0 FROM Sectionclass s JOIN s.schedules sch " +
           "WHERE s.semester.id = :semesterId " +
           "AND sch.dayOfWeek = :dayOfWeek " +
           "AND ((sch.startPeriod <= :endP AND sch.endPeriod >= :startP))")
    boolean existsConflict(
            @Param("semesterId") String semesterId,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("startP") int startP,
            @Param("endP") int endP
    );
    @Query("SELECT COUNT(s) > 0 FROM Sectionclass s JOIN s.schedules sch " +
           "WHERE s.instructor.id = :instructorId " +
           "AND s.semester.id = :semesterId " +
           "AND sch.dayOfWeek = :dayOfWeek " +
           "AND ((sch.startPeriod <= :endP AND sch.endPeriod >= :startP))")
    boolean existsInstructorConflict(
            @Param("instructorId") String instructorId,
            @Param("semesterId") String semesterId,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("startP") int startP,
            @Param("endP") int endP
    );
    @Query("SELECT COUNT(std) FROM Sectionclass s JOIN s.currentStudents std WHERE s.id = :sectionId")
    long countStudentsInClass(@Param("sectionId") Long sectionId);

}
