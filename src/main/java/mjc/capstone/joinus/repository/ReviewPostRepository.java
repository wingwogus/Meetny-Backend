package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.review.ReviewPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {
    Optional<ReviewPost> findByPostId(Long postId);
    List<ReviewPost> findAllByReviewerId(Long reviewerId);
    boolean existsByPostId(Long postId);

    @Query("""
    SELECT t.reviewTag.tagName, COUNT(t)
    FROM ReviewPost r
    JOIN r.mannerTags t
    WHERE r.post.author.id = :memberId
    GROUP BY t.reviewTag.tagName
    """)
    List<Object[]> countTagsByPostAuthorId(@Param("memberId") Long memberId);

    @Query("SELECT r FROM ReviewPost r WHERE r.post.author.id = :memberId")
    List<ReviewPost> findAllByToMemberId(@Param("memberId") Long memberId);
}
