package mjc.capstone.joinus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.tags.PostTag;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    private String photo;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "post")
    private PostTag postTag;

    public Post(Member member, String title, String content, String photo, Address address, PostTag postTag) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.photo = photo;
        this.address = address;
        this.postTag = postTag;
    }
}
