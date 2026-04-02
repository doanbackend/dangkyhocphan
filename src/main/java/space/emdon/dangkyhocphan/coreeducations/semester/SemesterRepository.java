package space.emdon.dangkyhocphan.coreeducations.semester;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
	Optional<Semester> findByName(String name);
	List<Semester> findAll();
}
