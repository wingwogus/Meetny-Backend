package mjc.capstone.joinus.domain;

import jakarta.persistence.*;
import lombok.*;
import mjc.capstone.joinus.domain.tags.UserTag;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "USER_ID")
    private String id;

    private String password;

    private String nickname;

    private String phone;

    private String mail;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "user")
    private UserTag userTag;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Post> posts = new ArrayList<>();

    @Builder
    public User(String id, String password, String nickname, String phone, String mail, Gender gender, Address address, UserTag userTag) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.mail = mail;
        this.gender = gender;
        this.address = address;
        this.userTag = userTag;
    }


}
