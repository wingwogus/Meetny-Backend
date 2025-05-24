package mjc.capstone.joinus.dto.mypage;

import mjc.capstone.joinus.domain.entity.Member;



public record FollowDto(Long memberId, String nickname, String username) {
    public static FollowDto from(Member member) {
        return new FollowDto(member.getId(), member.getNickname(), member.getUsername());
    }
}