package mjc.capstone.joinus.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SignInDto {
    private String username;
    private String password;
}
