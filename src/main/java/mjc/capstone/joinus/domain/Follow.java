package mjc.capstone.joinus.domain;

import jakarta.persistence.*;

@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FROM_Member_ID")
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "TO_Member_ID")
    private Member toMember;
}
