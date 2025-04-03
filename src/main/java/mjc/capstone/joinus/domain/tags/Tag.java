package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Tag {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String tagName;

    private String color;
}
