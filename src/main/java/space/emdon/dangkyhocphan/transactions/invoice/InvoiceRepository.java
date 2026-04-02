package space.emdon.dangkyhocphan.transactions.invoice;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	@Modifying
	@Query("UPDATE Invoice i SET i.paid = :paid WHERE i.id = :id")
	int updatePaidStatus(@Param("id") String id, @Param("paid") boolean paid);
	
	@Modifying
	@Query("UPDATE Invoice i SET i.paymentDate = :paymentDate WHERE i.id = :id")
	int updatePaymentDate(@Param("id") String id, @Param("paymentDate") java.time.LocalDate paymentDate);
	
	@Query("SELECT i FROM Invoice i WHERE i.student.numbered = :studentNumber")
	List<Invoice> findByStudentNumber(@Param("studentNumber") String studentNumber);
	
	@Query("SELECT i FROM Invoice i WHERE i.paid = :paid")
	List<Invoice> findByPaidStatus(@Param("paid") boolean paid);
	
	void deleteById(String id);
}
