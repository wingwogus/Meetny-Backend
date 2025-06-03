package mjc.capstone.joinus.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.review.ReviewPost;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReviewResponseDto {
    @Schema(description = "리뷰어 ID")
    private Long reviewerId;
    @Schema(description = "리뷰 코멘트")
    private String comment;
    @Schema(description = "리뷰어 닉네임")
    private String reviewerNickname;
    @Schema(description = "게시글 제목")
    private String postTitle;
    @Schema(description = "게시글 사진")
    private String photo;
    @Schema(description = "게시글 만남 시간")
    private LocalDateTime meetingTime;
    @Schema(description = "만남 장소")
    private Address address;
    @Schema(description = "매너 태그 목록")
    private List<String> mannerTag = new ArrayList<>();

    public static ReviewResponseDto from(ReviewPost review){
        return ReviewResponseDto.builder()
                .reviewerId(review.getReviewer().getId())
                .comment(review.getComment())
                .photo(review.getPost().getPhoto())
                .meetingTime(review.getPost().getMeetingTime())
                .address(review.getPost().getAddress())
                .reviewerNickname(review.getReviewer().getNickname())
                .postTitle(review.getPost().getTitle())
                .mannerTag(
                        review.getMannerTags().stream()
                                .map(tag -> tag.getReviewTag().getTagName())
                                .toList()
                )
                .build();
    }
}
