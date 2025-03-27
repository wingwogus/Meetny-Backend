package mjc.capstone.joinus.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;


class JwtServiceTest {

    private JwtService jwtService;

    // 테스트용 비밀키 (최소 32바이트 이상 권장)
    private final String testSecretKey = "TestSecretKey1234567890!@#$%^&*()";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // @Value 필드를 강제로 주입
        ReflectionTestUtils.setField(jwtService, "secretKey", testSecretKey);
    }

    @Test
    void testGenerateAndValidateToken() {
        // given
        String username = "testuser";

        // when
        String token = jwtService.generateToken(username);
        String validatedUsername = jwtService.validateToken(token);

        // then
        assertNotNull(token);
        assertEquals(username, validatedUsername);
    }

    @Test
    void testInvalidTokenThrowsException() {
        // given
        String invalidToken = "fake.token.value";

        // when & then
        assertThrows(SignatureException.class, () -> {
            jwtService.validateToken(invalidToken);
        });
    }

    @Test
    void testExpiredToken() throws InterruptedException {
        // given: 유효시간 1초짜리 토큰 발급
        ReflectionTestUtils.setField(jwtService, "secretKey", testSecretKey);

        String token = Jwts.builder()
                .setSubject("testuser")
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000)) // 1초 후 만료
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, testSecretKey.getBytes())
                .compact();

        // wait for token to expire
        Thread.sleep(1500);

        // then
        assertThrows(ExpiredJwtException.class, () -> {
            jwtService.validateToken(token);
        });
    }
}