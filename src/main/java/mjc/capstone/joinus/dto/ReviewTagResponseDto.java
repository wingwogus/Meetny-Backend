package mjc.capstone.joinus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mjc.capstone.joinus.domain.review.ReviewTagType;

@Getter
@AllArgsConstructor
public class ReviewTagResponseDto {
    private String tagName;

    private ReviewTagType type;
}
