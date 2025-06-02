package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);


    @Query("SELECT p FROM Post p JOIN FETCH p.tag WHERE p.tag = :tag")
    List<Post> findByTag(@Param("tag") Tag tag);

    List<Post> findByAuthor(Member member);
}
