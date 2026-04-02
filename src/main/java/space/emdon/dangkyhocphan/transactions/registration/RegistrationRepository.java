package space.emdon.dangkyhocphan.transactions.registration;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.emdon.dangkyhocphan.enums.RegistrationStatus;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, String> {
	// @Query - Check exists by section class id
	@Query("SELECT COUNT(r) > 0 FROM Registration r WHERE r.sectionclass.id = :sectionId")
	boolean existsBySectionClassId(@Param("sectionId") String sectionId);
	
	// @Query - Find by student number
	@Query("SELECT r FROM Registration r WHERE r.student.numbered = :studentNumber")
	List<Registration> findByStudentNumber(@Param("studentNumber") String studentNumber);
	
	// @Query - Find by section class id
	@Query("SELECT r FROM Registration r WHERE r.sectionclass.id = :sectionClassId")
	List<Registration> findBySectionClassId(@Param("sectionClassId") String sectionClassId);
	
	// @Query - Find by status
	@Query("SELECT r FROM Registration r WHERE r.status = :status")
	List<Registration> findByStatus(@Param("status") RegistrationStatus status);
	
	// @Query Update - Change status only (Soft delete preparation)
	@Modifying
	@Query("UPDATE Registration r SET r.status = :status WHERE r.id = :id")
	int updateStatus(@Param("id") String id, @Param("status") RegistrationStatus status);
}
