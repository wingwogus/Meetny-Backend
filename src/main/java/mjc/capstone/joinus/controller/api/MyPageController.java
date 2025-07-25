package mjc.capstone.joinus.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.dto.ApiResponse;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import mjc.capstone.joinus.dto.mypage.MyPageDto;
import mjc.capstone.joinus.dto.mypage.ProfileRequest;
import mjc.capstone.joinus.dto.tag.TagResponseDto;
import mjc.capstone.joinus.dto.tag.UserTagDto;
import mjc.capstone.joinus.service.inf.MemberService;
import mjc.capstone.joinus.service.inf.MyPageService;
import mjc.capstone.joinus.service.inf.PostService;
import mjc.capstone.joinus.service.inf.ReviewService;
import org.apache.juli.logging.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
@Tag(name = "MyPage API", description = "마이페이지 관련 API")
public class MyPageController {

    private final MyPageService myPageService;
    private final PostService postService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    // 프로필 이미지 수정
    @Operation(summary = "프로필 이미지 수정", description = "프로필 이미지를 넘겨받아 DB에 저장합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공",
            content = @Content(schema = @Schema(implementation = String.class)))
    @PutMapping("/profile/edit")
    public ResponseEntity<ApiResponse<String>> updateProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(ApiResponse.success("프로필 이미지 수정 완료",myPageService.profileEdit(profileRequest.getImageUrl(), userDetails.getUsername())));
    }

    // 계정 정보 페이지
    @Operation(summary = "계정 정보 조회", description = "로그인한 유저의 계정 정보와 게시글 목록을 반환합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = MyPageDto.class)))
    @GetMapping("/information")
    public ResponseEntity<ApiResponse<MyPageDto>> getInformation(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MyPageDto userDetailDto = myPageService.findMemberDto(userDetails.getMember());
        userDetailDto.setPosts(postService.getPostsByMember(userDetailDto.getNickname()));
        userDetailDto.setCredibility(reviewService.getCredibility(userDetails.getMember().getNickname()));
        return ResponseEntity.ok(ApiResponse.success(userDetailDto));
    }

    @Operation(summary = "계정 정보 조회", description = "다른 유저의 계정 정보와 게시글 목록을 반환합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = MyPageDto.class)))
    // 계정 정보 페이지
    @GetMapping("/information/{username}")
    public ResponseEntity<ApiResponse<MyPageDto>> getOtherInformation(
            @PathVariable String username,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member targetMember = memberService.findMemberByNickname(username);

        MyPageDto userDetailDto = myPageService.findMemberDto(targetMember);
        userDetailDto.setPosts(postService.getAllPosts(targetMember.getId()));
        userDetailDto.setCredibility(reviewService.getCredibility(username));
        return ResponseEntity.ok(ApiResponse.success(userDetailDto));
    }

    // 비밀번호 수정
    @Operation(summary = "비밀번호 수정", description = "비밀번호를 8~20자 내로 수정합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공 또는 실패 사유 메시지 반환",
            content = @Content(schema = @Schema(implementation = String.class)))
    @PutMapping("/information/password")
    public ResponseEntity<ApiResponse<String>> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody ProfileRequest profileRequest) {
        if(profileRequest.getPassword().length()<20 && profileRequest.getPassword().length()>8) {
            myPageService.editPassword(userDetails.getMember(), userDetails.getMember().getPassword(), profileRequest.getPassword());
            return ResponseEntity.ok(ApiResponse.success("비밀번호가 변경되었습니다!", null));
        }
        else {
            return ResponseEntity.ok(ApiResponse.success("비밀번호는 8~20자 길이입니다.", null));
        }
    }

    // 태그 정보 뿌리기
    @Operation(summary = "사용자 태그 조회", description = "로그인한 사용자의 태그 정보를 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TagResponseDto.class))))
    @GetMapping("/information/tag")

    public ResponseEntity<ApiResponse<List<TagResponseDto>>> getTags(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("태그 정보",myPageService.findAlltags(userDetails.getMember())));
    }

    // 태그 수정
    @Operation(summary = "사용자 태그 수정", description = "`add` 또는 `remove` 명령에 따라 태그를 추가 또는 삭제합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공",
            content = @Content(schema = @Schema(implementation = String.class)))
    @PutMapping("/information/tag/edit")
    public ResponseEntity<ApiResponse<String>> updateTag(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserTagDto userTagDto) {
        if(userTagDto.getAction().equals("add")) {
            myPageService.tagAdd(userTagDto.getTags(), userDetails.getMember());
        }
        else if(userTagDto.getAction().equals("remove")) {
            myPageService.tagRemove(userTagDto.getTags(), userDetails.getMember());
        }
        return ResponseEntity.ok(ApiResponse.success("수정 되었습니다.", null));
    }
}

