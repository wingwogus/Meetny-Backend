package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.dto.TagDto;
import mjc.capstone.joinus.dto.MyPageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MyPageService {
    //프로필 이미지 수정
    String profileEdit(String url, String username);
    // 유저 불러오기
    Member findMemberByUsername(String username);
    // 전체 태그 불러오기
    List<TagDto> findAlltags(Member member);
    // 태그 불러오기
    List<TagDto> findusertags(Member member);
    // 태그 추가
    void tagAdd(List<TagDto> tags,Member member);
    // 태그 삭제
    void tagRemove(List<TagDto> tags, Member member);
    // 유저 정보 가져오기
    MyPageDto findMemberDto(Member Member);
    // 팔로워 조회
    Long findFollowers(Member member);
    // 팔로잉 조회
    Long findFollowing(Member member);
    // 비밀번호 수정
    void editPassword(Member member, String oldPassword, String newPassword);
    // 동행 횟수 조회

    // 동행 후기 조회

    // 포스트 조회
}
