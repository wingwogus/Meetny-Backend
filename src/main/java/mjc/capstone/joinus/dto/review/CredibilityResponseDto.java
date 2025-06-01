package mjc.capstone.joinus.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CredibilityResponseDto {
    @Schema(description = "신뢰도")
    private Double credibility;
}
