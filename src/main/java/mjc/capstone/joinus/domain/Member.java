package mjc.capstone.joinus.domain;

import jakarta.persistence.*;
import lombok.*;
import mjc.capstone.joinus.domain.tags.UserTag;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Member {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false, updatable = false)
    private Long id;

    private String username;

    @Column(nullable = false)
    private String password;

    private String nickname;

    private String phone;

    private String mail;

    private String profileImg;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "member")
    private UserTag userTag;

    @OneToMany(mappedBy = "fromMember", fetch = FetchType.LAZY)
    List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "toMember", fetch = FetchType.LAZY)
    List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    List<Post> posts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;
}
