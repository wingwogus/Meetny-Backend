package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    List<Post> findByTag(Tag tag);

    List<Post> findByAuthor(Member member);
}
