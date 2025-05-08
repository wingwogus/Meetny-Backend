package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByMemberAndPost(Member member, Post post);

    int countByPost(Post post);

    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);
}
