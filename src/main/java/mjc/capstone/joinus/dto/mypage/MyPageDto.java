package mjc.capstone.joinus.dto.mypage;

import lombok.*;
import mjc.capstone.joinus.dto.post.PostResponseDto;
import mjc.capstone.joinus.dto.review.CredibilityResponseDto;
import mjc.capstone.joinus.dto.tag.TagResponseBasicDto;
import mjc.capstone.joinus.dto.tag.TagResponseDto;
import mjc.capstone.joinus.dto.tag.TagResponseDto;

import java.util.List;

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
    private List<TagResponseBasicDto> tags;

    private CredibilityResponseDto credibility;

    private Long followerCount;
    private Long followingCount;

    private List<PostResponseDto> posts;

}
