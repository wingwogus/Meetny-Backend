package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.tags.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
