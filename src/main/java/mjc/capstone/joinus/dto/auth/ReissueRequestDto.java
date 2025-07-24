package mjc.capstone.joinus.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReissueRequestDto {
    @Schema(description = "새로 발급된 Access 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", required = true)
    private String accessToken;

    @Schema(description = "새로 발급된 Refresh 토큰", example = "dGhpc2lzbXlyZWZyZXNoVG9rZW4=", required = true)
    private String refreshToken;
}
