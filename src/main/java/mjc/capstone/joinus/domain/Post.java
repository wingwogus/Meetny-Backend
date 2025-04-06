package mjc.capstone.joinus.domain;

import jakarta.persistence.*;
import lombok.*;
import mjc.capstone.joinus.domain.tags.PostTag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    private String title;

    private String content;

    private String photo;

    private LocalDateTime meetingTime;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "post")
    private PostTag postTag;

}
