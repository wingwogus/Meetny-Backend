package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.domain.tags.Tag;
import mjc.capstone.joinus.dto.PostLikeResponseDto;
import mjc.capstone.joinus.dto.PostRequestDto;
import mjc.capstone.joinus.dto.PostResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface PostService {
    // 포스트 생성
    void createPost(PostRequestDto post, Long memberId);
    // 포스트 업데이트
    void updatePost(Post post, PostRequestDto requestDto, Long memberId);
    // 포스트 삭제
    void deletePost(Post post, Long memberId);
    // 모든 포스트 불러오기
    List<PostResponseDto> getAllPosts(Long memberId);
    // 태그별 포스트 불러오기
    List<PostResponseDto> getPostByTag(Tag tag, Long memberId);
    // 기간별 포스트 불러오기
    List<PostResponseDto> getPostsByDateRange(LocalDateTime from, LocalDateTime to, Long memberId);
    // 포스트 좋아요 토글 기능
    PostLikeResponseDto togglePostLike(Post post, Long memberId);
    //List<PostResponseDto> searchPosts(String keyword);
    // 포스트 유효성 검증
    void validateAuth(Post post, Member member);

    // 포스트 조회 기능
    PostResponseDto getPostDetail(Post post, Long memberId);
}
