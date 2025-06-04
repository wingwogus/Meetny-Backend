package mjc.capstone.joinus.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.entity.Member;
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
import java.util.Base64;
import java.nio.charset.StandardCharsets;
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

    public JwtToken generateToken(Member member) {
        try {
            System.out.println("✅ JwtTokenProvider.generateToken 호출됨: " + member.getEmail());

            String accessToken = Jwts.builder()
                    .setSubject(member.getUsername())
                    .claim("auth", member.getRole().name()) // ← 여기가 null이면 터짐
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            String refreshToken = Jwts.builder()
                    .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            System.out.println("🟢 토큰 생성 완료");
            return new JwtToken("Bearer", accessToken, refreshToken);
        } catch (Exception e) {
            System.out.println("❌ 토큰 생성 실패: " + e.getMessage());
            e.printStackTrace();
            throw e; // 이거 꼭 던져줘야 Spring Security 쪽에서 후속 처리를 함
        }
    }

    public JwtToken reissueToken(String accessToken, String refreshToken) {
        validateToken(refreshToken);
        Authentication authentication = getAuthentication(accessToken);
        String storedRefreshToken = redisService.getValues("RT:" + authentication.getName())
                .orElseThrow(() -> new InvalidTokenException("유효하지 않은 Refresh Token 입니다."));

        if (!storedRefreshToken.equals(refreshToken)) {
            throw new InvalidTokenException("Refresh Token이 일치하지 않습니다.");
        }

        long now = (new Date()).getTime();
        String newAccessToken = generateAccessToken(authentication, now);
        long refreshTokenExpiration = parseClaims(refreshToken).getExpiration().getTime();

        if(refreshTokenExpiration - now < Duration.ofDays(3).toMillis()) {
            log.info("리프레쉬 토큰 재발급");
            refreshToken = generateRefreshToken(authentication, now);
        }

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateAccessToken(Authentication authentication, long now) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("nickname", principal.getNickname())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 3600000L))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Authentication authentication, long now) {
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 604800000L))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        redisService.setValues("RT:" + authentication.getName(), refreshToken, Duration.ofDays(7));
        return refreshToken;
    }

    public Authentication getAuthentication(String accessToken) {
        log.debug("🔑 getAuthentication() 호출됨 - AccessToken: {}", accessToken);
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new InvalidTokenException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        CustomUserDetails principal = (CustomUserDetails) customUserDetailsService.loadUserByUsername(claims.getSubject());
        log.debug("✅ 인증 완료 - 사용자: {}, 권한: {}", principal.getUsername(), authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public void validateToken(String token) {
        try {
            log.debug("🔍 검증 중인 토큰: {}", token);
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("❌ JWT 검증 실패 - 잘못된 서명 또는 구조: {}", e.getMessage());
            throw new InvalidTokenException("유효하지 않은 Token 입니다.");
        } catch (ExpiredJwtException e) {
            log.warn("⚠️ 만료된 JWT: {}", e.getMessage());
            throw new InvalidTokenException("만료된 Token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("❌ 지원하지 않는 JWT 형식: {}", e.getMessage());
            throw new InvalidTokenException("지원하지 않는 Token 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("❌ 빈 토큰 입력됨: {}", e.getMessage());
            throw new InvalidTokenException("Token의 내용이 없습니다.");
        } catch (Exception e) {
            log.error("❌ 예외 발생 - 토큰 검증 실패: {}", e.getMessage());
            throw new InvalidTokenException("유효하지 않은 Token 입니다.");
        }
    }

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