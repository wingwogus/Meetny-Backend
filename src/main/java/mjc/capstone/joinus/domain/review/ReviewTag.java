package mjc.capstone.joinus.domain.review;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class ReviewTag {

    public ReviewTag(String tagName) {
        this.tagName = tagName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String tagName;
}
