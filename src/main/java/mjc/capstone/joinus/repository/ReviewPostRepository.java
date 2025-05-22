package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.review.ReviewPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {
    Optional<ReviewPost> findByPostId(Long postId);
    List<ReviewPost> findAllByReviewerId(Long reviewerId);
}
