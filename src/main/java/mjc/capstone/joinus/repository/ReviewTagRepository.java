package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.review.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {
    boolean existsByTagName(String tagName);
}
