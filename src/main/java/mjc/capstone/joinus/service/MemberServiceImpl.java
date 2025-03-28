package mjc.capstone.joinus.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.Address;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.jwt.JwtToken;
import mjc.capstone.joinus.jwt.JwtTokenProvider;
import mjc.capstone.joinus.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public JwtToken signIn(String username, String password) {
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
