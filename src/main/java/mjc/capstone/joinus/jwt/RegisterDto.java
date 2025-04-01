package mjc.capstone.joinus.jwt;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.Gender;
import mjc.capstone.joinus.domain.Member;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterDto {
    private String username;

    private String password;

    private String nickname;

    private String phone;

    private String mail;

    private String profileImg;

    private Gender gender;

    private Address address;

    public Member toEntity(String encodedPassword, List<String> roles) {
        return Member.builder()
                .username(username)
                .password(encodedPassword)
                .nickname(nickname)
                .phone(phone)
                .mail(mail)
                .profileImg(profileImg)
                .gender(gender)
                .address(address)
                .roles(roles)
                .build();
    }
}
