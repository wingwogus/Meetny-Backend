package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.Follow;
import mjc.capstone.joinus.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByFromMember(Member fromMember);
    List<Follow> findByToMember(Member toMember);
    Optional<Follow> findByFromMemberAndToMember(Member fromMember, Member toMember);
    void deleteByFromMemberAndToMember(Member fromMember, Member toMember);
    long countByFromMember(Member fromMember);
    long countByToMember(Member toMember);
}