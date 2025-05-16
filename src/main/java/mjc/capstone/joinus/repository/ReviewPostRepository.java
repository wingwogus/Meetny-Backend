package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.review.ReviewPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {
}
