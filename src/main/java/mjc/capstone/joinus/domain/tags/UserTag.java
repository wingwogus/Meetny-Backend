package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.Member;

import java.util.List;
import java.util.ArrayList;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTag {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private Member member;

    @OneToMany
    @JoinColumn(name = "TAG_ID")
    private List<Tag> tags = new ArrayList<>();
}
