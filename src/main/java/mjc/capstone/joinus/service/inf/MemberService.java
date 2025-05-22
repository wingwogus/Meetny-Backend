package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.dto.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    void signup(SignupRequest request);
}