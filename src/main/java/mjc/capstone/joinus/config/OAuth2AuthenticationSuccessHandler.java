package mjc.capstone.joinus.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.jwt.JwtTokenProvider;
import mjc.capstone.joinus.jwt.JwtToken;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    private final String frontendRedirectUrl = "http://localhost:3000"; // 프론트 URL

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("✅ 핸들러 진입");

        Object principal = authentication.getPrincipal();
        System.out.println("🔍 Principal 클래스: " + principal.getClass());

        if (!(principal instanceof CustomUserDetails)) {
            System.out.println("⛔ 예상치 못한 Principal 타입: " + principal);
            response.sendRedirect("http://localhost:3000/login?error=unexpected_principal");
            return;
        }

        CustomUserDetails userDetails = (CustomUserDetails) principal;
        System.out.println("🔥 Principal 타입: " + authentication.getPrincipal().getClass());
        Member member = userDetails.getMember();

        if (member == null) {
            System.out.println("⛔ member가 null입니다.");
            response.sendRedirect("http://localhost:3000/login?error=null_member");
            return;
        }

        System.out.println("✅ 로그인한 사용자: " + member.getUsername());

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        System.out.println("✅ 토큰 생성 완료");

        String accessToken = jwtToken.getAccessToken();
        String refreshToken = jwtToken.getRefreshToken();
        String encodedEmail = URLEncoder.encode(member.getUsername(), StandardCharsets.UTF_8.toString());

        String redirectUrl;
        if (member.getPhone() == null || member.getPhone().isEmpty()) {
            redirectUrl = UriComponentsBuilder
                    .fromUriString(frontendRedirectUrl + "/social-register")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .queryParam("email", encodedEmail)
                    .build().toUriString();
        } else {
            redirectUrl = UriComponentsBuilder
                    .fromUriString(frontendRedirectUrl + "/information")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build().toUriString();
        }

        System.out.println("🟢 리다이렉트 URL: " + redirectUrl);

        response.sendRedirect(redirectUrl);
    }

}