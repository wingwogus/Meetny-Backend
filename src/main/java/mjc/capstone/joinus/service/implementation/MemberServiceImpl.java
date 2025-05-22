
package mjc.capstone.joinus.service.implementation;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Role;
import mjc.capstone.joinus.dto.auth.LoginRequestDto;
import mjc.capstone.joinus.dto.auth.ReissueRequestDto;
import mjc.capstone.joinus.dto.auth.SignUpRequestDto;
import mjc.capstone.joinus.exception.InvalidCredentialsException;
import mjc.capstone.joinus.jwt.JwtToken;
import mjc.capstone.joinus.jwt.JwtTokenProvider;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.inf.MemberService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly=false)
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisService redisService;

    @Override
    public JwtToken login(LoginRequestDto request) {
        /// 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword());

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public JwtToken reissue(ReissueRequestDto request) {
        // 1. RefreshToken 유효성 검사
        jwtTokenProvider.validateToken(request.getRefreshToken());

        // 4. 새 토큰 생성
        return jwtTokenProvider.reissueToken(request.getAccessToken(), request.getRefreshToken());
    }

    @Override
    public void signup(SignUpRequestDto request) {
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        Member member = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .phone(request.getPhone())
                .mail(request.getMail())
                .profileImg(request.getProfileImg())
                .gender(request.getGender())
                .address(request.getAddress())
                .role(Role.USER)
                .build();
        memberRepository.save(member);
    }

    @Override
    public void logout(String email) {
        Optional<String> refreshToken = redisService.getValues("RT:" + email);
        if (refreshToken.isEmpty()) {
            throw new InvalidCredentialsException("로그인되어 있지 않은 상태입니다");
        }

        redisService.deleteValues("RT:" + email);
    }
}
