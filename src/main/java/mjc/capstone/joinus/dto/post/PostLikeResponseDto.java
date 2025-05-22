package mjc.capstone.joinus.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostLikeResponseDto {
    private boolean liked;
    private int likeCount;

}
