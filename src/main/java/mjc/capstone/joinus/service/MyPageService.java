package mjc.capstone.joinus.service;

import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.UserDetailDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public interface MyPageService {
    //프로필 이미지 수정
    String profileEdit(String url, Member member);
    // 태그 추가
    void tagAdd(Tag tag, Member member);
    // 태그 삭제
    void tagRemove(Tag tag, Member member);
    // 유저 정보 가져오기
    UserDetailDto findMember(String username);
    // 팔로워 조회
    void findFollowers(User user);
    // 팔로잉 조회
    void findFollowing(User user);
    // 동행 횟수 조회

    // 동행 후기 조회

    //
}
