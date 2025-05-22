package mjc.capstone.joinus.config;

// This configuration class sets up security settings for the application,
// including authentication, authorization, and session management.
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.dto.CustomUserDetails;
import mjc.capstone.joinus.jwt.JwtAuthenticationFilter;
import mjc.capstone.joinus.service.implementation.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF(사이트 간 요청 위조) 보호 비활성화 (API 방식이므로 비활성화)
                .csrf(csrf -> csrf.disable())

                // 세션 관리 정책 설정: 필요할 때만 세션 생성
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 인가 규칙 설정: 로그인/회원가입 API는 인증 없이 접근 허용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/follows/**",
                                "/api/chatroom/**",
                                "/ws",
                                "/ws/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                        .requestMatchers("/information/tag/edit").authenticated()
                        .anyRequest().authenticated()) // 그 외 모든 요청은 인증 필요

                // 사용자 정의 로그인 필터 추가
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화 방식: BCrypt 사용
        return new BCryptPasswordEncoder();
    }
}