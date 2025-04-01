package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.dto.MemberDto;
import mjc.capstone.joinus.jwt.JwtToken;
import mjc.capstone.joinus.jwt.MemberLoginRequestDto;
import mjc.capstone.joinus.jwt.RegisterDto;
import mjc.capstone.joinus.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public JwtToken login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        JwtToken jwtToken = memberService.login(memberLoginRequestDto);

        //로그용
        String username = memberLoginRequestDto.getUsername();
        String password = memberLoginRequestDto.getPassword();
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return jwtToken;
    }

    @PostMapping("/register")
    public ResponseEntity<MemberDto> register(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(memberService.register(registerDto));
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

}
