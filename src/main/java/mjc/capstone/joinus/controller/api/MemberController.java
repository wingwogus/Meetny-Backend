package mjc.capstone.joinus.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Operation(summary = "로그아웃", description = "로그아웃 처리 및 저장된 RefreshToken 삭제")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal UserDetails userDetails) {
        memberService.logout(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("로그아웃 성공", null));
    }
}

