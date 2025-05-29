package mjc.capstone.joinus.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.review.ReviewPost;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewerId;
    private String comment;
    private String reviewerNickname;
    private String postTitle;
    private String photo;
    private LocalDateTime meetingTime;
    private List<String> mannerTag = new ArrayList<>();

    public static ReviewResponseDto from(ReviewPost review){
        return ReviewResponseDto.builder()
                .reviewerId(review.getReviewer().getId())
                .comment(review.getComment())
                .photo(review.getPost().getPhoto())
                .meetingTime(review.getPost().getMeetingTime())
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
