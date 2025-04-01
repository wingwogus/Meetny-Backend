package mjc.capstone.joinus.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class MemberLoginRequestDto {
    private String username;
    private String password;
}
