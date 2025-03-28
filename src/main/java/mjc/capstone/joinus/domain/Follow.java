package mjc.capstone.joinus.domain;

import jakarta.persistence.*;

@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private Member toMember;
}
