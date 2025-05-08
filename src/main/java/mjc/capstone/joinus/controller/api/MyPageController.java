package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.dto.*;
import mjc.capstone.joinus.service.inf.MyPageService;
import mjc.capstone.joinus.service.inf.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final PostService postService;

    // 프로필 이미지 수정
    @PutMapping("/profile/edit")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetails userDetails,@RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(myPageService.profileEdit(profileRequest.getImageUrl(), userDetails.getUsername()));
    }

    // 계정 정보 페이지
    @GetMapping("/information")
    public ResponseEntity<MyPageDto> getInformation(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MyPageDto userDetailDto = myPageService.findMemberDto(userDetails.getMember());
        userDetailDto.setPosts(postService.getAllPosts(userDetails.getMember().getId()));
        return ResponseEntity.ok(userDetailDto);
    }
    // 비밀번호 수정
    @PutMapping("/information/password")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody ProfileRequest profileRequest) {
        if(profileRequest.getPassword().length()<20 && profileRequest.getPassword().length()>8) {
            myPageService.editPassword(userDetails.getMember(), userDetails.getMember().getPassword(), profileRequest.getPassword());
            return ResponseEntity.ok("비밀번호가 변경되었습니다!");
        }
        else {
            return ResponseEntity.ok("비밀번호는 8~20자 길이입니다.");
        }

    }
    // 태그 정보 뿌리기
    @GetMapping("/information/tag")
    public ResponseEntity<List<TagDto>> getTags(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(myPageService.findAlltags(userDetails.getMember()));
    }
    // 태그 수정
    @PutMapping("/information/tag/edit")
    public ResponseEntity<String> updateTag(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserTagDto userTagDto) {
        if(userTagDto.getAction().equals("add")) {
            myPageService.tagAdd(userTagDto.getTags(), userDetails.getMember());
        }
        else if(userTagDto.getAction().equals("remove")) {
            myPageService.tagRemove(userTagDto.getTags(), userDetails.getMember());
        }
        return ResponseEntity.ok("수정 되었습니다.");
    }
}

