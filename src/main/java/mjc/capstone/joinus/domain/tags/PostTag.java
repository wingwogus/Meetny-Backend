package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Post;
import mjc.capstone.joinus.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @OneToMany
    @JoinColumn(name = "TAG_ID")
    private List<Tag> tags = new ArrayList<>();
}
