package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import mjc.capstone.joinus.dto.review.ReviewRequestDto;
import mjc.capstone.joinus.dto.review.ReviewResponseDto;
import mjc.capstone.joinus.service.inf.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{postId}")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long postId,
            @RequestBody ReviewRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        ReviewResponseDto review = reviewService.createReview(userDetails.getMember().getId(), postId, dto);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long reviewId) {
        ReviewResponseDto review = reviewService.getReview(reviewId);
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        ReviewResponseDto review = reviewService.getReview(reviewId);
        if (!review.getReviewerNickname().equals(userDetails.getMember().getNickname())) {
            return ResponseEntity.status(403).body(null);
        }

        ReviewResponseDto updatedReview = reviewService.updateReview(reviewId, dto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        ReviewResponseDto review = reviewService.getReview(reviewId);
        if (!review.getReviewerNickname().equals(userDetails.getMember().getNickname())) {
            return ResponseEntity.status(403).build();
        }

        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tags/count")
    public ResponseEntity<Map<String, Long>> getMannerTagCounts(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Map<String, Long> tagCounts = reviewService.getMannerTagCounts(userDetails.getMember().getId());
        return ResponseEntity.ok(tagCounts);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAvailableTags() {
        // 서버에서 지정된 태그 목록
        List<String> tags = List.of("친절함", "시간 약속을 잘지킴", "소통이 원활함", "매너 좋음");
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ReviewResponseDto> getReviewByPost(
            @PathVariable Long postId) {

        ReviewResponseDto review = reviewService.getPostReview(postId);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewByMember(
            @PathVariable Long memberId) {

        List<ReviewResponseDto> reviews = reviewService.getMemberReviews(memberId);
        return ResponseEntity.ok(reviews);
    }
}
