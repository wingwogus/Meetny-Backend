package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.entity.Follow;
import mjc.capstone.joinus.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFromMemberId(Long fromUserId);
    List<Follow> findByToMemberId(Long toUserId);
    Optional<Follow> findByFromMemberAndToMember(Member fromMember, Member toMember);
    void deleteByFromMemberAndToMember(Member fromMember, Member toMember);
    long countByFromMember(Member fromMember);
    long countByToMember(Member toMember);

    @Query("SELECT f.toMember FROM Follow f WHERE f.fromMember.id = :userId")
    List<Member> findFollowingsByUserId(@Param("userId") Long userId);

    @Query("SELECT f.fromMember FROM Follow f WHERE f.toMember.id = :userId")
    List<Member> findFollowersByUserId(@Param("userId") Long userId);
}
