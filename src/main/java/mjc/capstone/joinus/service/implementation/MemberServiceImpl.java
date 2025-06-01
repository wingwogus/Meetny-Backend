
package mjc.capstone.joinus.service.implementation;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Role;
import mjc.capstone.joinus.dto.auth.LoginRequestDto;
import mjc.capstone.joinus.dto.auth.ReissueRequestDto;
import mjc.capstone.joinus.dto.auth.SignUpRequestDto;
import mjc.capstone.joinus.dto.auth.VerifiedRequestDto;
import mjc.capstone.joinus.exception.*;
import mjc.capstone.joinus.jwt.JwtToken;
import mjc.capstone.joinus.jwt.JwtTokenProvider;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.inf.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisService redisService;
    private final MailService mailService;

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private static final String VERIFIED_EMAIL_PREFIX = "VerifiedEmail ";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Transactional(readOnly=true)
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

    @Transactional(readOnly=true)
    @Override
    public JwtToken reissue(ReissueRequestDto request) {
        // 1. RefreshToken 유효성 검사
        jwtTokenProvider.validateToken(request.getRefreshToken());

        // 4. 새 토큰 생성
        return jwtTokenProvider.reissueToken(request.getAccessToken(), request.getRefreshToken());
    }

    @Transactional
    @Override
    public void signup(SignUpRequestDto request) {
        memberRepository.findByUsername(request.getUsername())
                .ifPresent(a -> {
                    throw new IllegalArgumentException("이미 존재하는 아이디입니다.");});
        String isSuccess = redisService.getValues(VERIFIED_EMAIL_PREFIX + request.getUsername())
                .orElseThrow(() -> new NotVerifiedEmailException("인증되지 않은 이메일입니다."));

        if (!isSuccess.equals("true")) {
            throw new NotVerifiedEmailException("인증되지 않은 이메일입니다.");
        }

        Member member = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .phone(request.getPhone())
                .profileImg(request.getProfileImg())
                .gender(request.getGender())
                .address(request.getAddress())
                .role(Role.USER)
                .credibility(45.0)
                .build();

        memberRepository.save(member);
    }

    @Override
    public void sendCodeToEmail(String toEmail) {
        checkDuplicatedEmail(toEmail);
        String title = "MEETNY 이메일 인증 번호";
        String authCode = createCode();
        mailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(authCodeExpirationMillis));
    }

    private void checkDuplicatedEmail(String email) {
        Optional<Member> member = memberRepository.findByUsername(email);
        if (member.isPresent()) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new DuplicateEmailException(email);
        }
    }

    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "코드 생성 오류 발생");
        }
    }

    @Override
    public void verifiedCode(VerifiedRequestDto verifiedRequestDto) {
        String email = verifiedRequestDto.getEmail();
        String authCode = verifiedRequestDto.getCode();

        this.checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email)
                .orElseThrow(() -> new VerificationFailedException("인증코드가 존재하지 않습니다. 다시 요청해주세요."));

        if (!redisAuthCode.equals(authCode)) {
            throw new VerificationFailedException("인증코드가 일치하지 않습니다.");
        }

        redisService.setValues(VERIFIED_EMAIL_PREFIX + email, "true");
    }

    @Transactional
    @Override
    public void logout(String email) {
        Optional<String> refreshToken = redisService.getValues("RT:" + email);
        if (refreshToken.isEmpty()) {
            throw new InvalidTokenException("로그인되어 있지 않은 상태입니다");
        }

        redisService.deleteValues("RT:" + email);
    }
}
