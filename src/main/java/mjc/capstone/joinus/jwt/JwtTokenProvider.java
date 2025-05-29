package mjc.capstone.joinus.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import mjc.capstone.joinus.exception.InvalidTokenException;
import mjc.capstone.joinus.service.implementation.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import mjc.capstone.joinus.service.implementation.CustomUserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private final RedisService redisService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            RedisService redisService,
                            CustomUserDetailsService customUserDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisService = redisService;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtToken generateToken(Authentication authentication) {

        long now = (new Date()).getTime();

        // Access Token 생성
        String accessToken = generateAccessToken(authentication, now);

        String refreshToken = generateRefreshToken(authentication, now);

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtToken reissueToken(String accessToken, String refreshToken) {
        // Refresh Token 유효성 검증
        validateToken(refreshToken);

        // Access Token에서 사용자 정보 추출 (비록 만료되었더라도 subject는 꺼낼 수 있음)
        Authentication authentication = getAuthentication(accessToken);

        // Redis에 저장된 RefreshToken과 일치하는지 확인
        String storedRefreshToken = redisService.getValues("RT:" + authentication.getName())
                .orElseThrow(() -> new InvalidTokenException("유효하지 않은 Refresh Token 입니다."));

        if (!storedRefreshToken.equals(refreshToken)) {
            throw new InvalidTokenException("Refresh Token이 일치하지 않습니다.");
        }

        long now = (new Date()).getTime();

        // 새로운 AccessToken 생성
        String newAccessToken = generateAccessToken(authentication, now);

        long refreshTokenExpiration = parseClaims(refreshToken).getExpiration().getTime();

        if(refreshTokenExpiration - now < Duration.ofDays(3).toMillis()) {
            log.info("리프레쉬 토큰 재발급");
            refreshToken = generateRefreshToken(authentication, now);
        }

        // 기존 RefreshToken은 그대로 재사용
        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // RefreshToken과 AccessToken을 기반으로 새로운 AccessToken을 발급하는 메서드
    private String generateAccessToken(Authentication authentication, long now) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())         // 토큰의 주체 (보통 username)
                .claim("auth", authorities)                // 유저 권한 추가
                .setExpiration(new Date(now + 60 * 60 * 1000L))          // 만료 시간 1시간 설정
                .signWith(key, SignatureAlgorithm.HS256)      // 서명 (key는 비밀키)
                .compact();
    }

    private String generateRefreshToken(Authentication authentication, long now) {
        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 7 * 24 * 60 * 60 * 1000L)) // 유효기간 일주일
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Redis에 Refresh Token 일주일간 저장
        redisService.setValues("RT:" + authentication.getName(), refreshToken, Duration.ofDays(7));
        return refreshToken;
    }


    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new InvalidTokenException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // CustomUserDetails 객체를 만들어서 Authentication return
        CustomUserDetails principal = (CustomUserDetails) customUserDetailsService
                .loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new InvalidTokenException("유효하지 않은 Token 입니다.");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("만료된 Token 입니다.");
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenException("지원하지 않는 Token 입니다.");
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("Token의 내용이 없습니다.");
        }
    }


    // token
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}