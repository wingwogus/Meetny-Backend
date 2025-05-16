package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.dto.ReviewRequestDto;
import mjc.capstone.joinus.dto.ReviewResponseDto;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.inf.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    @PostMapping("/{postId}")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long postId,
            @RequestBody ReviewRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Member fromMember = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid logged-in user"));

        ReviewResponseDto review = reviewService.createReview(fromMember.getId(), postId, dto);
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
            @AuthenticationPrincipal UserDetails userDetails) {

        Member fromMember = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid logged-in user"));

        ReviewResponseDto review = reviewService.getReview(reviewId);
        if (!review.getReviewerNickname().equals(fromMember.getNickname())) {
            return ResponseEntity.status(403).body(null);
        }

        ReviewResponseDto updatedReview = reviewService.updateReview(reviewId, dto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Member fromMember = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid logged-in user"));

        ReviewResponseDto review = reviewService.getReview(reviewId);
        if (!review.getReviewerNickname().equals(fromMember.getNickname())) {
            return ResponseEntity.status(403).build();
        }

        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tags/count")
    public ResponseEntity<Map<String, Long>> getMannerTagCounts(
            @AuthenticationPrincipal UserDetails userDetails) {

        Member fromMember = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid logged-in user"));

        Map<String, Long> tagCounts = reviewService.getMannerTagCounts(fromMember.getId());
        return ResponseEntity.ok(tagCounts);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAvailableTags() {
        // 서버에서 지정된 태그 목록
        List<String> tags = List.of("친절함", "시간 약속을 잘지킴", "소통이 원활함", "매너 좋음");
        return ResponseEntity.ok(tags);
    }
}
