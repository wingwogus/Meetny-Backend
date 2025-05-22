package mjc.capstone.joinus.domain.review;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class ReviewPostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_post_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_post_id", nullable = false)
    private ReviewPost reviewPost;

    @ManyToOne
    @JoinColumn(name = "review_tag_id", nullable = false)
    private ReviewTag reviewTag;
}
