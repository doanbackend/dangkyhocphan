package space.emdon.dangkyhocphan.repository;

import org.springframework.stereotype.Repository;

import space.emdon.dangkyhocphan.entity.InvalidatedToken;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {

}
