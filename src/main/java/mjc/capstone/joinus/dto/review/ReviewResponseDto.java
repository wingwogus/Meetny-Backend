package mjc.capstone.joinus.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.review.ReviewPost;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReviewResponseDto {
    private String comment;
    private String reviewerNickname;
    private String postTitle;
    private List<String> mannerTag = new ArrayList<>();

    public static ReviewResponseDto from(ReviewPost review){
        return ReviewResponseDto.builder()
                .comment(review.getComment())
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
