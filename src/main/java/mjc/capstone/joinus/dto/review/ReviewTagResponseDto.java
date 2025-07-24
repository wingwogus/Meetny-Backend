package mjc.capstone.joinus.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mjc.capstone.joinus.domain.review.ReviewTagType;

@Getter
@AllArgsConstructor
public class ReviewTagResponseDto {
    @Schema(description = "매너 태그 아이디")
    private Long id;
    @Schema(description = "매너 태그 이름")
    private String tagName;
    @Schema(description = "매너 태그 타입")
    private ReviewTagType type;
}
