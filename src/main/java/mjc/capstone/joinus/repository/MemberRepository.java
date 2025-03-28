package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {
}
