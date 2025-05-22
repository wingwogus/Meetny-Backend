package mjc.capstone.joinus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.entity.Gender;
import mjc.capstone.joinus.domain.tags.MemberTag;

@Data
@AllArgsConstructor
@Builder
public class SignupRequest {
    private String username;
    private String nickname;
    private String password;
    private String phone;
    private String mail;
    private String profileImg;
    private Gender gender;
    private Address address;
    private MemberTag memberTag;
}
