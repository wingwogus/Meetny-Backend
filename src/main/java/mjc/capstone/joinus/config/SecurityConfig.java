package mjc.capstone.joinus.config;

// This configuration class sets up security settings for the application,
// including authentication, authorization, and session management.
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF(사이트 간 요청 위조) 보호 비활성화 (API 방식이므로 비활성화)
                .csrf(csrf -> csrf.disable())

                // 세션 관리 정책 설정: 필요할 때만 세션 생성
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1))

                // 인가 규칙 설정: 로그인/회원가입 API는 인증 없이 접근 허용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/api/signup", "/api/test").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                        .requestMatchers("/follows/**").permitAll()
                        .requestMatchers("/information/tag/edit").authenticated()
                        .anyRequest().authenticated()) // 그 외 모든 요청은 인증 필요
                // 사용자 정의 로그인 필터 추가
                .addFilterAt(jsonLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        // 인증을 관리할 ProviderManager 반환
        return new ProviderManager(provider);
    }

    public JsonUsernamePasswordAuthenticationFilter jsonLoginFilter(AuthenticationManager authenticationManager) {
        JsonUsernamePasswordAuthenticationFilter filter =
                new JsonUsernamePasswordAuthenticationFilter(authenticationManager);

        // 로그인 성공 시 200 OK 반환
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
        });

        // 로그인 실패 시 401 Unauthorized 반환
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        });

        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화 방식: BCrypt 사용
        return new BCryptPasswordEncoder();
    }
}