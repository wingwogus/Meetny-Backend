package mjc.capstone.joinus.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.domain.tags.Role;
import mjc.capstone.joinus.dto.LoginRequest;
import mjc.capstone.joinus.dto.SignupRequest;
import mjc.capstone.joinus.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        Member member = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        memberRepository.save(member);
    }

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .username("wingwogus")
                .password(passwordEncoder.encode("1234"))
                .role(Role.USER)
                .build();

        memberRepository.save(member);
    }
}
