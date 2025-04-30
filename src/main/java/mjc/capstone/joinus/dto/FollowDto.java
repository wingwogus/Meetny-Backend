package mjc.capstone.joinus.dto;

import mjc.capstone.joinus.domain.Member;

public record FollowDto(Long memberId, String nickname, String username) {
    public static FollowDto from(Member member) {
        return new FollowDto(member.getId(), member.getNickname(), member.getUsername());
    }
}