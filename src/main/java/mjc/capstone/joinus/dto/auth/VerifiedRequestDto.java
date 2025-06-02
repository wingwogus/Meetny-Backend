package mjc.capstone.joinus.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VerifiedRequestDto {
    @Schema(description = "코드를 받은 이메일", example = "example@naver.com")
    private String email;

    @Schema(description = "이메일로 받은 인증번호", example = "616481")
    private String code;
}
