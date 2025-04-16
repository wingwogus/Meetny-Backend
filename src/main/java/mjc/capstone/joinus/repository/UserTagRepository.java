package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.tags.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    Optional<UserTag> findByMember(Member member);

    @Query("SELECT t.id FROM UserTag ut JOIN ut.tags t WHERE ut.member = :member")
    List<Long> findTagIdsByMember(@Param("member") Member member);
}

