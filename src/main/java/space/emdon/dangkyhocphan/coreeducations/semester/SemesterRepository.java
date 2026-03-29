package space.emdon.dangkyhocphan.coreeducations.semester;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
Optional<Semester> findByName(String name);
}
