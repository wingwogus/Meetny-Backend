package mjc.capstone.joinus.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.review.ReviewPost;
import mjc.capstone.joinus.domain.review.ReviewPostTag;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewRequestDto {
    private String comment;
    private List<Long> mannerTags;

    public ReviewPost toEntity(Member member) {
        return ReviewPost.builder()
                .comment(this.comment)
                .reviewer(member)
                .build();
    }

    public void updateReviewPost(ReviewPost reviewPost, List<ReviewPostTag> reviewTags){
        reviewPost.setComment(this.comment);
        reviewPost.getMannerTags().clear();
        reviewPost.getMannerTags().addAll(reviewTags);
    }
}
