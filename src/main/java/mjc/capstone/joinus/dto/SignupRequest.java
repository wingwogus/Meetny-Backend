package mjc.capstone.joinus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
}
