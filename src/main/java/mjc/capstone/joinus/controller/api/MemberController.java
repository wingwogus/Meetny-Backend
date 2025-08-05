package mjc.capstone.joinus.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.dto.ApiResponse;
import mjc.capstone.joinus.dto.auth.*;
import mjc.capstone.joinus.jwt.JwtToken;
import mjc.capstone.joinus.service.inf.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "아이디과 비밀번호로 로그인합니다.")
    public ResponseEntity<ApiResponse<JwtToken>> login(@RequestBody LoginRequestDto loginRequestDto) {
        JwtToken response = memberService.login(loginRequestDto);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", response));
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "RefreshToken으로 AccessToken을 재발급합니다.")
    public ResponseEntity<ApiResponse<JwtToken>> reissue(@RequestBody ReissueRequestDto request) {
        JwtToken newToken = memberService.reissue(request);
        return ResponseEntity.ok(ApiResponse.success("RefreshToken 재발급 성공", newToken));
    }

    @PostMapping("/send-email")
    @Operation(summary = "이메일 인증 코드 전송", description = "입력된 이메일로 인증 코드를 전송합니다.")
    public ResponseEntity<ApiResponse<Void>> sendMessage(@RequestBody EmailRequestDto emailRequestDto) {
        memberService.sendCodeToEmail(emailRequestDto.getEmail());
        return ResponseEntity.ok(ApiResponse.success("이메일 전송에 성공하였습니다", null));
    }

    @PostMapping("/verification")
    @Operation(summary = "이메일 인증 코드 확인", description = "사용자가 입력한 인증 코드를 확인합니다.")
    public ResponseEntity<ApiResponse<Void>> verification(@RequestBody VerifiedRequestDto verifiedRequestDto) {
        memberService.verifiedCode(verifiedRequestDto);
        return ResponseEntity.ok(ApiResponse.success("코드 인증에 성공하였습니다.", null));
    }

    @PostMapping("/verification-nickname")
    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 여부를 확인합니다.")
    public ResponseEntity<ApiResponse<Void>> verificationNickname(@RequestBody VerifiedNicknameRequest verifiedRequestDto) {
        memberService.checkDuplicatedNickname(verifiedRequestDto);
        return ResponseEntity.ok(ApiResponse.success("사용 가능한 닉네임입니다!", null));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일 인증을 마친 사용자를 등록합니다.")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody SignUpRequestDto signUpRequest) {
        memberService.signup(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입에 성공하였습니다", null));
    }

    @PostMapping("/")
    @Operation(summary = "토큰 검증 테스트", description = "AccessToken을 통한 사용자 확인 테스트입니다.")
    public ResponseEntity<String> test(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUsername());
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 처리 및 저장된 RefreshToken을 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal UserDetails userDetails) {
        memberService.logout(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("로그아웃 성공", null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "유저 간단 정보 조회", description = "id를 통해 유저의 간단한 정보(닉네임, 신뢰도, 이미지, 태그)를 조회합니다.")
    public ResponseEntity<ApiResponse<SimpleMemberInfoDto>> getSimpleMemberInfo(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("유저 정보 조회 성공", memberService.findMemberById(id)));
    }

    @PostMapping("/withdraw")
    @Operation(summary = "회원 탈퇴", description = "현재 로그인 된 회원을 탈퇴시킵니다.")
    public ResponseEntity<ApiResponse<Void>> withdrawMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
        memberService.withdraw(userDetails.getMember().getId());

        return ResponseEntity.ok(ApiResponse.success("회원 탈퇴 완료", null));
    }
}

