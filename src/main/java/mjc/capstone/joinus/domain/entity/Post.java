package mjc.capstone.joinus.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import mjc.capstone.joinus.domain.tags.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter @Setter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member author;

    private String title;

    private String content;

    private String photo;

    private LocalDateTime meetingTime;

    private int viewCount = 0;

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

    /* 연관관계 편의 메소드 */
    public void setAuthor(Member member) {
        this.author = member;
        member.getPosts().add(this); // 여기서도 양방향 유지
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
