package mjc.capstone.joinus.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import mjc.capstone.joinus.domain.entity.Member;

import java.util.List;

@Data
@Builder
public class SimpleMemberInfoDto {

    @Schema(description = "닉네임")
    String nickname;

    @Schema(description = "신뢰도")
    Double credibility;

    @Schema(description = "프로필 이미지")
    String profileImg;

    @Schema(description = "보유 태그")
    List<String> tags;

    public static SimpleMemberInfoDto from(Member member) {
        return SimpleMemberInfoDto.builder()
                .nickname(member.getNickname())
                .credibility(member.getCredibility())
                .profileImg(member.getProfileImg())
                .tags(member.getMemberTag().stream()
                        .map(tag -> tag.getTags().getTagName())
                        .toList())
                .build();

    }
}
