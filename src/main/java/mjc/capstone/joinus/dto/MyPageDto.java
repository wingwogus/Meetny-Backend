package mjc.capstone.joinus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mjc.capstone.joinus.domain.entity.Post;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MyPageDto {
    private String nickname;
    private String email;
    private String profilePic;
    private String password;
    private String phone;
    private List<String> tags;

    private Long followerCount = 0L;
    private Long followingCount = 0L;

    private List<Post> posts;

}
