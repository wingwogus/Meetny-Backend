package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.config.SecurityConfig;
import mjc.capstone.joinus.domain.Member;
import mjc.capstone.joinus.dto.ProfileRequest;
import mjc.capstone.joinus.dto.UserDetailDto;
import mjc.capstone.joinus.dto.UserTagDto;
import mjc.capstone.joinus.service.MyPageServiceIml;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageServiceIml myPageService;

    // 프로필 이미지 수정
    @PutMapping("/profile/edit")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetails userDetails,@RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(myPageService.profileEdit(profileRequest.getImageUrl(), userDetails.getUsername()));
    }

    // 계정 정보 페이지
    @GetMapping("/information")
    public ResponseEntity<UserDetailDto> getInformation(@AuthenticationPrincipal UserDetails userDetails) {
        UserDetailDto userDetailDto = myPageService.findMemberDto(userDetails.getUsername());
        return ResponseEntity.ok(userDetailDto);
    }
    // 비밀번호 수정
    @PutMapping("/informaion/password")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal UserDetails userDetails,@RequestBody ProfileRequest profileRequest) {
        Member member = myPageService.findMemberByUsername(userDetails.getUsername());
        if(profileRequest.getPassword().length()<20 && profileRequest.getPassword().length()>8) {
            myPageService.editPassword(member, member.getPassword(), profileRequest.getPassword());
            return ResponseEntity.ok("비밀번호가 변경되었습니다!");
        }
        else {
            return ResponseEntity.ok("비밀번호는 8~20자 길이입니다.");
        }

    }
    // 태그 수정
    @PutMapping("/tag/edit")
    public ResponseEntity<String> updateTag(@AuthenticationPrincipal UserDetails userDetails) {
        UserDetailDto userDetailDto = myPageService.findMemberDto(userDetails.getUsername());
        return ResponseEntity.ok("");
    }
}
