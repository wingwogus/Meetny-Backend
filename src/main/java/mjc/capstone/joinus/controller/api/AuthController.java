package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Address;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import mjc.capstone.joinus.dto.auth.SignUpRequestDto;
import mjc.capstone.joinus.dto.auth.SocialRegisterDto;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.inf.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import mjc.capstone.joinus.exception.InvalidTokenException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepository;

    @GetMapping("/check-profile")
    public ResponseEntity<?> checkProfile(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        boolean isComplete = member.getPhone() != null && !member.getPhone().isEmpty();

        Map<String, Object> response = new HashMap<>();
        response.put("profileComplete", isComplete);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/social-register")
    public ResponseEntity<?> updateSocialInfo(@RequestBody SocialRegisterDto dto,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new InvalidTokenException("인증 정보가 없습니다. 유효한 토큰을 포함해 요청해주세요.");
        }
        Member member = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("소셜 유저를 찾을 수 없습니다."));

        member.setNickname(dto.getNickname());
        member.setPhone(dto.getPhone());
        member.setGender(dto.getGender());
        member.setAddress(dto.getAddress());
        member.setBirthdate(dto.getBirthdate()); // Member에도 필드 있어야 함

        memberRepository.save(member);

        return ResponseEntity.ok().body(Map.of("message", "등록 완료"));
    }
}