package mjc.capstone.joinus.repository;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.entity.PostView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostViewRepository extends JpaRepository<PostView, Long> {
    boolean existsByPostAndMember(Post post, Member member);
}
