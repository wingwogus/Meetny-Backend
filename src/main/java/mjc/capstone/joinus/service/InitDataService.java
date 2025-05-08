package mjc.capstone.joinus.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.Gender;
import mjc.capstone.joinus.dto.SignupRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDataService {
    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.signup(
                SignupRequest.builder()
                .username("userid1")
                .password("1234")
                .nickname("거인이재현")
                .gender(Gender.MALE)
                .phone("01073382156")
                .build());
        memberService.signup(
                SignupRequest.builder()
                .username("userid2")
                .password("1234")
                .nickname("소인이재현")
                .gender(Gender.MALE)
                .phone("01073382156")
                .build());
        memberService.signup(
                SignupRequest.builder()
                .username("userid3")
                .password("1234")
                .nickname("대인이재현")
                .gender(Gender.MALE)
                .phone("01073382156")
                .build());

    }
}
