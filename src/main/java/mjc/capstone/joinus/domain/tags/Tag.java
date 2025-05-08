package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@RequiredArgsConstructor
public abstract class Tag {

    protected Tag(String tagName, String color) {
        this.tagName = tagName;
        this.color = color;
    }

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String tagName;

    private String color;

    @OneToMany
    @JoinColumn(name = "member_tag_id")
    private List<MemberTag> memberTag;

}
