package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.tags.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    Optional<UserTag> findByMember(Member member);
}
