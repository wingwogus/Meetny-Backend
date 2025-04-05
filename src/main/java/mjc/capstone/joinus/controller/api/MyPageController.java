package mjc.capstone.joinus.controller.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.config.SecurityConfig;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.dto.ProfileRequest;
import mjc.capstone.joinus.dto.UserDetailDto;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.MyPageService;
import mjc.capstone.joinus.service.MyPageServiceIml;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageServiceIml myPageService;
    private final MemberRepository memberRepository;
    private final SecurityConfig securityConfig;

    // 프로필 이미지 수정
    @PutMapping("/profile/edit")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ProfileRequest profileRequest) {
        Member member = memberRepository.findByUsername(securityConfig.getLoginUser())
                .orElseThrow(() -> new UsernameNotFoundException("세션 내에 유저가 없습니다."));
        return ResponseEntity.ok(myPageService.profileEdit(profileRequest.getImageUrl(), member));
    }
    // 계정 정보 페이지
    @GetMapping("/information")
    public ResponseEntity<UserDetailDto> getInformation(@AuthenticationPrincipal UserDetails userDetails) {
        UserDetailDto userDetailDto = myPageService.findMember(userDetails.getUsername());
        return ResponseEntity.ok(userDetailDto);
    }
    // 태그 수정
}
