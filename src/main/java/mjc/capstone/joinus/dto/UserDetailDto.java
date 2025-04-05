package mjc.capstone.joinus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDetailDto {
    private String nickname;
    private String email;
    private String profilePic;
    private String password;
    private String phone;
}
