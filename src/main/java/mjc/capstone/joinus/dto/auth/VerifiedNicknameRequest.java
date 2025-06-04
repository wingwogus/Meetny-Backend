package mjc.capstone.joinus.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VerifiedNicknameRequest {
    @Schema(description = "검증할 닉네임", example = "cool_nick", required = true)
    private String nickname;
}
