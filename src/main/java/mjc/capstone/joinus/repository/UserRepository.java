package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
