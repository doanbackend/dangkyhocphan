package space.emdon.dangkyhocphan.coreeducations.schedule;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	@Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.dayOfWeek = :dayOfWeek AND s.startPeriod = :startPeriod AND s.endPeriod = :endPeriod AND s.room = :room")
	boolean existsByDayOfWeekAndStartPeriodAndEndPeriodAndRoom(@Param("dayOfWeek") DayOfWeek dayOfWeek, @Param("startPeriod") int startPeriod, @Param("endPeriod") int endPeriod, @Param("room") String room);

	@Query("SELECT s FROM Schedule s WHERE s.sectionclass.name = :sectionclassName")
	List<Schedule> findBySectionclassName(@Param("sectionclassName") String sectionclassName);


	Optional<Schedule> findById(Long id);

	@Query("SELECT s FROM Schedule s WHERE s.dayOfWeek = :dayOfWeek ORDER BY s.startPeriod")
	List<Schedule> findByDayOfWeekOrderByStartPeriod(@Param("dayOfWeek") DayOfWeek dayOfWeek);
	
	@Modifying
	@Query("UPDATE Schedule s SET s.startPeriod = :startPeriod, s.endPeriod = :endPeriod WHERE s.id = :id")
	int updateSchedulePeriod(@Param("id") Long id, @Param("startPeriod") int startPeriod, @Param("endPeriod") int endPeriod);
	
	void deleteById(Long id);
}
