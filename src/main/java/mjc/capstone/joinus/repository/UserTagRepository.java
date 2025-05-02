package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.tags.MemberTag;
import mjc.capstone.joinus.dto.TagDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTagRepository extends JpaRepository<MemberTag, Long> {
    Optional<MemberTag> findByMember(Member member);

    @Query("SELECT new mjc.capstone.joinus.dto.TagDto(t.id, t.tagName) " +
            "FROM MemberTag ut JOIN ut.tags t WHERE ut.member = :member")
    List<TagDto> findTagsByMember(@Param("member") Member member);


    @Query("SELECT t.id FROM MemberTag ut JOIN ut.tags t WHERE ut.member = :member")
    List<Long> findTagIdsByMember(@Param("member") Member member);

    @Query("SELECT mt FROM MemberTag mt WHERE mt.member = :member AND mt.tags.id IN :tagIds")
    List<MemberTag> findByMemberAndTagIds(@Param("member") Member member, @Param("tagIds") List<Long> tagIds);
}

