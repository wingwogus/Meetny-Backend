package mjc.capstone.joinus.config;

// This configuration class sets up security settings for the application,
// including authentication, authorization, and session management.
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // TODO: Inject these beans as needed (implementations should be provided elsewhere)
    private final org.springframework.security.oauth2.client.userinfo.OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF(사이트 간 요청 위조) 보호 비활성화 (API 방식이므로 비활성화)
                .csrf(AbstractHttpConfigurer::disable)

                // 세션 관리 정책 설정: 필요할 때만 세션 생성
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인가 규칙 설정: 로그인/회원가입 API는 인증 없이 접근 허용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/follows/**",
                                "/api/chatroom/**",
                                "/ws",
                                "/ws/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/api/tags/**",
                                "/swagger-ui.html",
                                "/swagger-ui.html",
                                "/oauth2/**",
                                "/login/**").permitAll()
                        .requestMatchers("/api/members/information/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                        .requestMatchers("/follows/**").permitAll()
                        .requestMatchers("/api/my-page/**").authenticated()
                        .requestMatchers("/information/tag/edit").authenticated()
                        .anyRequest().authenticated()) // 그 외 모든 요청은 인증 필요

                // OAuth2 Login configuration
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                )

                // 사용자 정의 로그인 필터 추가
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Ignore favicon.ico from security
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/favicon.ico");
    }
}