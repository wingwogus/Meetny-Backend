package mjc.capstone.joinus.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.entity.Gender;
import mjc.capstone.joinus.domain.tags.MemberTag;

@Data
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    @Schema(description = "회원가입 id", example = "user1")
    private String username;

    @Schema(description = "닉네임", example = "거인이재현")
    private String nickname;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @Schema(description = "휴대폰 번호", example = "01012345678")
    private String phone;

    @Schema(description = "이메일", example = "example@naver.com")
    private String mail;

    @Schema(description = "프로필 사진", example = "example.png")
    private String profileImg;

    @Schema(description = "성별", example = "MALE, FEMALE")
    private Gender gender;

    @Schema(description = "주소", example = "주소")
    private Address address;

    @Schema(description = "멤버태그", example = "락, 콘서트")
    private MemberTag memberTag;
}
