package space.emdon.dangkyhocphan.transactions.registration;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, String> {

}
