package mjc.capstone.joinus.domain.review;

import jakarta.persistence.*;
import lombok.*;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter @Setter
public class ReviewPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_post_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "reviewer", nullable = false)
    private Member reviewer;

    @OneToOne
    @JoinColumn(name = "post", unique = true,nullable = false)
    private Post post;

    @OneToMany(mappedBy = "reviewPost", cascade = CascadeType.ALL)
    private List<ReviewPostTag> mannerTags = new ArrayList<>();

}
