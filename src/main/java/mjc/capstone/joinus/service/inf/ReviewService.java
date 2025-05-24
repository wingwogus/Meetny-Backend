package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.dto.review.ReviewRequestDto;
import mjc.capstone.joinus.dto.review.ReviewResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ReviewService {
    // 리뷰 생성
    ReviewResponseDto createReview(Long memberId, Long postId, ReviewRequestDto dto);

    // 리뷰 조회
    ReviewResponseDto getReview(Long reviewId);

    // 리뷰 수정
    ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto dto);

    // 리뷰 삭제
    void deleteReview(Long reviewId);

    // 매너태그 개수 조회
    Map<String, Long> getMannerTagCounts(Long memberId);

    //포스트 리뷰 보기
    ReviewResponseDto getPostReview(Long postId);

    //작성한 리뷰 보기
    List<ReviewResponseDto> getMemberReviews(Long memberId);
}
