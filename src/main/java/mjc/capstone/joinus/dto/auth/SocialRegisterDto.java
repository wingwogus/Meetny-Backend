package mjc.capstone.joinus.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.entity.Gender;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SocialRegisterDto {

    @Schema(description = "닉네임", example = "소셜유저닉")
    private String nickname;

    @Schema(description = "휴대폰 번호", example = "01012345678")
    private String phone;

    @Schema(description = "성별", example = "MALE, FEMALE")
    private Gender gender;

    @Schema(description = "주소", example = "서울특별시 강남구 역삼동")
    private Address address;

    @Schema(description = "프로필 사진", example = "example.png")
    private String profileImg;

    @JsonFormat(pattern = "yyyy-MM-dd") // 반드시 필요
    private LocalDate birthdate;
}