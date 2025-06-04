package mjc.capstone.joinus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Follow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_member_id")
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "to_user_member_id")
    private Member toMember;
}
