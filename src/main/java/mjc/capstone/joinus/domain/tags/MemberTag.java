package mjc.capstone.joinus.domain.tags;

import jakarta.persistence.*;
import lombok.*;
import mjc.capstone.joinus.domain.entity.Member;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class MemberTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tags;


}
