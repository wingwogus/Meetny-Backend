package mjc.capstone.joinus.domain;

import jakarta.persistence.*;
import lombok.*;
import mjc.capstone.joinus.domain.tags.Tag;

import java.time.LocalDateTime;

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

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "tag_id")
    private Tag postTag;
}
