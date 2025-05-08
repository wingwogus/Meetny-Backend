package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {



    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.memberTag WHERE m.username = :username")
    Optional<Member> findByUsername(@Param("username") String username);
}
