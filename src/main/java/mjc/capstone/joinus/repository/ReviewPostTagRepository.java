package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.review.ReviewPostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewPostTagRepository extends JpaRepository<ReviewPostTag, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ReviewPostTag r WHERE r.reviewPost.id = :reviewPostId")
    void deleteByReviewPostId(Long reviewPostId);
}
