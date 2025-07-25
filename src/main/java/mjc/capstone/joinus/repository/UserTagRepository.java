package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.tags.MemberTag;
import mjc.capstone.joinus.dto.tag.TagDto;
import mjc.capstone.joinus.dto.tag.TagResponseBasicDto;
import mjc.capstone.joinus.dto.tag.TagResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTagRepository extends JpaRepository<MemberTag, Long> {
    @Query("SELECT mt FROM MemberTag mt JOIN FETCH mt.tags WHERE mt.member = :member")
    Optional<MemberTag> findByMember(@Param("member") Member member);

    @Query("""
    SELECT new mjc.capstone.joinus.dto.tag.TagResponseDto(
        t.id,
        t.tagName,
        t.color,
        TYPE(t)
    )
    FROM MemberTag ut JOIN ut.tags t
    WHERE ut.member = :member
    """)
    List<TagResponseDto> findTagsByMember(@Param("member") Member member);

    @Query("""
    SELECT new mjc.capstone.joinus.dto.tag.TagResponseBasicDto(
        t.id,
        t.tagName,
        t.color,
        TYPE(t)
    )
    FROM MemberTag ut JOIN ut.tags t
    WHERE ut.member = :member
    """)
    List<TagResponseBasicDto> findBasicTagsByMember(@Param("member") Member member);


    @Query("SELECT t.id FROM MemberTag ut JOIN ut.tags t WHERE ut.member = :member")
    List<Long> findTagIdsByMember(@Param("member") Member member);

    @Query("SELECT mt FROM MemberTag mt JOIN FETCH mt.tags WHERE mt.member = :member AND mt.tags.id IN :tagIds")
    List<MemberTag> findByMemberAndTagIds(@Param("member") Member member, @Param("tagIds") List<Long> tagIds);

    // 페치 조인
    @Query("SELECT mt FROM MemberTag mt JOIN FETCH mt.tags WHERE mt.member = :member")
    List<MemberTag> findAllByMemberWithTags(@Param("member") Member member);
}

