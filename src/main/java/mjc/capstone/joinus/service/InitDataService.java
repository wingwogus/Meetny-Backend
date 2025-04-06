package mjc.capstone.joinus.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.Role;
import mjc.capstone.joinus.dto.SignupRequest;
import mjc.capstone.joinus.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDataService {
    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.signup(new SignupRequest("customuserid", "1234"));
    }
}
