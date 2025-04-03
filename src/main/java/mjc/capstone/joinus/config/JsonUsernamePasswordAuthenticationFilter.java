package mjc.capstone.joinus.config;

/**
 * JsonUsernamePasswordAuthenticationFilter는 JSON 형식의 사용자 이름과 비밀번호를 사용하여 인증을 처리하는 필터입니다.
 * 이 필터는 사용자의 로그인 요청을 가로채고, 인증을 수행합니다.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mjc.capstone.joinus.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 처리를 위한 ObjectMapper 인스턴스

    public JsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager); // AuthenticationManager 설정
        setFilterProcessesUrl("/api/login"); // API 경로 지정
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            // 요청의 InputStream에서 JSON 형식의 로그인 정보를 읽어 LoginRequest 객체로 변환
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            // UsernamePasswordAuthenticationToken 생성: 사용자 이름과 비밀번호를 사용하여 인증 요청 생성
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());
            // AuthenticationManager를 사용하여 인증 요청을 수행하고 결과를 반환
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            // JSON 파싱 중 오류 발생 시 RuntimeException 발생
            throw new RuntimeException(e);
        }
    }
}