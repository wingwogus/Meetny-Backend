package mjc.capstone.joinus.domain;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_view", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "post_id"})
})
public class PostView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private LocalDateTime viewedAt = LocalDateTime.now();

    protected PostView() {}

    public PostView(Post post, Member member) {
        this.post = post;
        this.member = member;
        this.viewedAt = LocalDateTime.now();
    }
}