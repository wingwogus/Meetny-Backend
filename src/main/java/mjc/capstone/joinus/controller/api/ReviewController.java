package mjc.capstone.joinus.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.dto.ApiResponse;
import mjc.capstone.joinus.dto.review.CredibilityResponseDto;
import mjc.capstone.joinus.dto.review.ReviewTagResponseDto;
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
@Tag(name = "Review API", description = "리뷰 작성, 조회, 수정, 삭제 기능을 제공합니다.")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{postId}")
    @Operation(summary = "리뷰 작성", description = "게시글에 대해 리뷰를 작성합니다. (참여자만 가능)")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @RequestBody ReviewRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        ReviewResponseDto review = reviewService.createReview(userDetails.getMember().getId(), postId, dto);
        return ResponseEntity.ok(ApiResponse.success("리뷰 작성 완료", review));
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 조회", description = "리뷰 ID를 통해 리뷰를 조회합니다.")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReview(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId) {
        ReviewResponseDto review = reviewService.getReview(reviewId);
        return ResponseEntity.ok(ApiResponse.success("리뷰 단일 조회 성공", review));
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "리뷰 ID를 바탕으로 리뷰를 수정합니다.")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
            @RequestBody ReviewRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        ReviewResponseDto updatedReview = reviewService.updateReview(reviewId, dto, userDetails.getMember());
        return ResponseEntity.ok(ApiResponse.success("리뷰 수정 성공", updatedReview));
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 ID를 바탕으로 리뷰를 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @Parameter(description = "리뷰 ID") @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        reviewService.deleteReview(reviewId, userDetails.getMember());
        return ResponseEntity.ok(ApiResponse.success("리뷰 삭제 성공", null));
    }

    @GetMapping("/tags/count/{memberId}")
    @Operation(summary = "회원의 매너 태그 개수 조회", description = "회원이 받은 매너 태그별 개수를 조회합니다.")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getMannerTagCounts(
            @Parameter(description = "회원 ID") @PathVariable Long memberId) {

        Map<String, Long> tagCounts = reviewService.getMannerTagCounts(memberId);
        return ResponseEntity.ok(ApiResponse.success("매너 태그 개수 조회 성공", tagCounts));
    }

    @GetMapping("/tags")
    @Operation(summary = "매너 태그 목록 조회", description = "리뷰 작성 시 선택할 수 있는 고정된 매너 태그 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<ReviewTagResponseDto>>> getAvailableTags() {
        return ResponseEntity.ok(ApiResponse.success("태그 목록 조회 성공", reviewService.getTags()));
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "게시글 기준 리뷰 조회", description = "게시글 ID를 기준으로 작성된 리뷰를 조회합니다.")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReviewByPost(
            @Parameter(description = "게시글 ID") @PathVariable Long postId) {

        ReviewResponseDto review = reviewService.getPostReview(postId);
        return ResponseEntity.ok(ApiResponse.success("게시글 별 리뷰 조회 성공", review));
    }

    @GetMapping("/write/{memberId}")
    @Operation(summary = "회원이 작성한 리뷰 목록 조회", description = "특정 회원이 작성한 모든 리뷰를 조회합니다.")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviewByMember(
            @Parameter(description = "회원 ID") @PathVariable Long memberId) {

        List<ReviewResponseDto> reviews = reviewService.getMemberReviews(memberId);
        return ResponseEntity.ok(ApiResponse.success("작성한 리뷰 목록 조회 성공", reviews));
    }

    @GetMapping("/credibility/{memberId}")
    @Operation(summary = "회원의 신뢰도 조회", description = "특정 회원의 신뢰도를 조회합니다.")
    public ResponseEntity<ApiResponse<CredibilityResponseDto>> getCredibility(
            @Parameter(description = "회원 ID") @PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.success("신뢰도 조회 성공", reviewService.getCredibility(memberId)));
    }

    @GetMapping("/receive/{memberId}")
    @Operation(summary = "회원이 받은 리뷰 목록 조회", description = "특정 회원이 받은 모든 리뷰를 조회합니다.")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReceive(
            @Parameter(description = "회원 ID") @PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.success("받은 리뷰 목록 조회 성공", reviewService.getReviewsAboutMe(memberId)));
    }

}
