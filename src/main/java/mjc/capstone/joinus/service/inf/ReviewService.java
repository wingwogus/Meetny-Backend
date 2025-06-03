package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.dto.review.*;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.review.ReviewTagType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ReviewService {
    // 리뷰 생성
    ReviewResponseDto createReview(Long memberId, Long postId, ReviewRequestDto dto);

    // 신뢰도 증감
    void calculateCredibility(Member author, List<ReviewTagType> tagTypes);

    // 신뢰도 되돌리기
    void revertCredibility(Member author, List<ReviewTagType> tagTypes);

    // 리뷰 조회
    ReviewResponseDto getReview(Long reviewId);

    // 리뷰 수정
    ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto dto, Member reviewer);

    // 리뷰 삭제
    void deleteReview(Long reviewId, Member reviewer);

    // 매너태그 개수 조회
    Map<String, Long> getMannerTagCounts(Long memberId);

    //포스트 리뷰 보기
    ReviewResponseDto getPostReview(Long postId);

    //작성한 리뷰 보기
    List<ReviewResponseDto> getMemberReviews(Long memberId);

    //받은 리뷰 보기
    List<ReviewResponseDto> getReviewsAboutMe(Long memberId);

    //신뢰도보기
    CredibilityResponseDto getCredibility(Long memberId);

    //태그목록
    List<ReviewTagResponseDto> getTags();

    //리뷰 대상 게시물 정보 조회
    public ReviewMetaResponseDto getReviewMeta(Long postId);
}
