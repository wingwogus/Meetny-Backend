package mjc.capstone.joinus.dto;

import lombok.*;
import mjc.capstone.joinus.dto.post.PostResponseDto;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class MyPageDto {
    private String nickname;
    private String email;
    private String profilePic;
    private String password;
    private String phone;
    private List<String> tags;

    private CredibilityResponseDto credibility;

    private Long followerCount = 0L;
    private Long followingCount = 0L;

    private List<PostResponseDto> posts;

}
