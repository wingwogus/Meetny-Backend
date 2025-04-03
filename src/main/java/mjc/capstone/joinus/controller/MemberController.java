package mjc.capstone.joinus.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.dto.SignInDto;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.MemberServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberServiceImpl memberService;

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String username = signInDto.getUsername();
        String password = signInDto.getPassword();
        JwtToken jwtToken = memberService.signIn(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/test")
    public String test(){
        return "success";
    }
}
