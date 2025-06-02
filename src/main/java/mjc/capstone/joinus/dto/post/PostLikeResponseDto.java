package mjc.capstone.joinus.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostLikeResponseDto {

    @Schema(description = "좋아요 여부", example = "true")
    private boolean liked;

    @Schema(description = "게시물 좋아요 수", example = "15")
    private int likeCount;
}
