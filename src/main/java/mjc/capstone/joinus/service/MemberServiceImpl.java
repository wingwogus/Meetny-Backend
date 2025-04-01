package mjc.capstone.joinus.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.dto.MemberDto;
import mjc.capstone.joinus.jwt.JwtToken;
import mjc.capstone.joinus.jwt.JwtTokenProvider;
import mjc.capstone.joinus.jwt.MemberLoginRequestDto;
import mjc.capstone.joinus.jwt.RegisterDto;
import mjc.capstone.joinus.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public JwtToken login(MemberLoginRequestDto memberLoginRequestDto) {
        String username = memberLoginRequestDto.getUsername();
        String password = memberLoginRequestDto.getPassword();
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    @Transactional
    @Override
    public MemberDto register(RegisterDto registerDto) {
        if (memberRepository.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");  // USER 권한 부여
        return MemberDto.toDto(memberRepository.save(registerDto.toEntity(encodedPassword, roles)));
    }

    //테스트 코드
    @PostConstruct
    public void init() {
        Member member1 = Member.builder()
                .username("wingwogus")
                .password("1234")
                .address(new Address("dd", "dd", "dd"))
                .roles(Arrays.asList("USER", "ADMIN"))
                .nickname("거인이재현")
                .build();

        Member member2 = Member.builder()
                .username("dkdkdk")
                .password("1234")
                .address(new Address("dd", "dd", "dd"))
                .roles(Arrays.asList("ADMIN"))
                .nickname("소인이재현")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
    }
}
