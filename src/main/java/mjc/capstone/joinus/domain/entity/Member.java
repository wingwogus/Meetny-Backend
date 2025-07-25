package mjc.capstone.joinus.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import mjc.capstone.joinus.domain.tags.MemberTag;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
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

    private String profileImg;

    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    @Column(nullable = false)
    @Builder.Default
    private Double credibility = 45.0;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberTag> memberTag = new ArrayList<>();

    @OneToMany(mappedBy = "fromMember", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "toMember", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PostLike> postLikes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    public void addMemberTag(MemberTag memberTag) {
        if (memberTag != null) {
            if (this.memberTag == null) {
                this.memberTag = new ArrayList<>();
            }
            this.memberTag.add(memberTag);
            memberTag.setMember(this); // 양방향 설정
        }
    }
}
