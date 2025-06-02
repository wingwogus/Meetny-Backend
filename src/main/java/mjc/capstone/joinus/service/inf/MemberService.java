package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.dto.auth.LoginRequestDto;
import mjc.capstone.joinus.dto.auth.ReissueRequestDto;
import mjc.capstone.joinus.dto.auth.SignUpRequestDto;
import mjc.capstone.joinus.dto.auth.VerifiedRequestDto;
import mjc.capstone.joinus.jwt.JwtToken;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    JwtToken login(LoginRequestDto request);
    JwtToken reissue(ReissueRequestDto request);
    void signup(SignUpRequestDto request);
    void sendCodeToEmail(String toEmail);

    void verifiedCode(VerifiedRequestDto verifiedRequestDto);

    void logout(String email);

}