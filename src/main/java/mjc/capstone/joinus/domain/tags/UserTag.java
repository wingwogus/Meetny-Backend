package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mjc.capstone.joinus.domain.Member;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTag {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @JoinColumn(name = "tag_id")
    private List<Tag> tags = new ArrayList<>();
}
