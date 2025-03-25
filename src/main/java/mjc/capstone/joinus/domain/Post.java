package mjc.capstone.joinus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.tags.PostTag;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private User user;

    private String title;

    private String content;

    private String photo;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "post")
    private PostTag postTag;

    public Post(User user, String title, String content, String photo, Address address, PostTag postTag) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.photo = photo;
        this.address = address;
        this.postTag = postTag;
    }
}
