package mjc.capstone.joinus.dto.mypage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ProfileRequest {
    private String imageUrl;
    private String password;
}
