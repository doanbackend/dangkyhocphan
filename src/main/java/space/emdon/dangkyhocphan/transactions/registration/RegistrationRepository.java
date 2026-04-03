package space.emdon.dangkyhocphan.transactions.registration;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import space.emdon.dangkyhocphan.enums.RegistrationStatus;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, String> {

@Query("SELECT COUNT(r) > 0 FROM Registration r WHERE r.sectionclass.name = :sectionclassName")
boolean existsBySectionClassName(@Param("sectionclassName") String sectionclassName);

@Query("SELECT r FROM Registration r WHERE r.student.numbered = :studentNumber")
List<Registration> findByStudentNumber(@Param("studentNumber") String studentNumber);

@Query("SELECT r FROM Registration r WHERE r.sectionclass.name = :sectionclassName")
List<Registration> findBySectionClassName(@Param("sectionclassName") String sectionclassName);

@Query("SELECT r FROM Registration r WHERE r.status = :status")
List<Registration> findByStatus(@Param("status") RegistrationStatus status);

@Modifying
@Query("UPDATE Registration r SET r.status = :status WHERE r.id = :id")
int updateStatus(@Param("id") String id, @Param("status") RegistrationStatus status);
}
