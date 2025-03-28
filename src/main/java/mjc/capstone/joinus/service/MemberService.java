package mjc.capstone.joinus.service;

import mjc.capstone.joinus.jwt.JwtToken;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

    @Transactional
    JwtToken signIn(String username, String password);
}
