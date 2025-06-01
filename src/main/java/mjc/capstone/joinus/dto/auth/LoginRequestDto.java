package mjc.capstone.joinus.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Schema(description = "로그인 ID", example = "user1")
    private String username;

    @Schema(description = "로그인 PASSWORD", example = "1234")
    private String password;
}
