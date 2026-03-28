package space.emdon.dangkyhocphan.coreeducations.subject;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);

    boolean existsByName(String name);
    boolean existsByCode(String code);
    boolean existsByCodeAndName(String code, String name);
    void deleteByCode(String code);

    Optional<Subject> findByCode(String code);

}
