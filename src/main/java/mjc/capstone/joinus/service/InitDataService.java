package mjc.capstone.joinus.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDataService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .username("wingwogus")
                .password(passwordEncoder.encode("1234"))
                .build();

        memberRepository.save(member);
    }
}
