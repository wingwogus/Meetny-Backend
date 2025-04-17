package mjc.capstone.joinus.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.Gender;
import mjc.capstone.joinus.domain.tags.UserTag;

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
    private UserTag userTag;
}
