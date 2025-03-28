package mjc.capstone.joinus.domain;

import jakarta.persistence.*;

@Entity
public class Follow {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FROM_USER_ID")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "TO_USER_ID")
    private User toUser;
}
