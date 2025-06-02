package mjc.capstone.joinus.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.dto.ApiResponse;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import mjc.capstone.joinus.dto.chat.ChatResponseDto;
import mjc.capstone.joinus.dto.chat.ChatRoomDto;
import mjc.capstone.joinus.service.inf.ChatRoomService;
import mjc.capstone.joinus.service.inf.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ChatRoom", description = "채팅룸 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/")
    @Operation(summary = "채팅 리스트", description = "현재 로그인 된 계정의 채팅방 목록을 불러옵니다.")
    public ResponseEntity<ApiResponse<List<ChatRoomDto>>> chatRoomList(
            @AuthenticationPrincipal CustomUserDetails userDetails){

        Long memberId = userDetails.getMember().getId();

        List<ChatRoomDto> chatRoomDtos = chatRoomService.chatRoomList(memberId);
        return ResponseEntity.ok(ApiResponse.success("채팅창 조회 성공", chatRoomDtos));
    }

    // 채팅방 입장
    @PostMapping("/{postId}")
    @Operation(summary = "채팅방 생성 및 입장", description = "현재 로그인 된 계정으로 포스트를 기반으로 채팅방을 생성합니다.")
    public ResponseEntity<ApiResponse<ChatRoomDto>> findOrCreateRoom(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();

        ChatRoomDto room = chatRoomService.findOrCreateRoom(postId, memberId);
        return ResponseEntity.ok(ApiResponse.success("채팅방 입장 성공", room));
    }

    @GetMapping("/history/{roomId}")
    public ResponseEntity<ApiResponse<List<ChatResponseDto>>> getChatHistory(
            @PathVariable String roomId) {

        return ResponseEntity.ok(ApiResponse.success("채팅 조회 성공", chatService.getChatList(roomId)));
    }

    @PostMapping("/{roomId}/complete")
    @Operation(summary = "동행 완료", description = "채팅방의 사람들끼리 동행을 완료합니다.")
    public ResponseEntity<ApiResponse<Void>> completeRoom(
            @PathVariable String roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();

        chatRoomService.completeRoom(roomId, memberId);

        return ResponseEntity.ok(ApiResponse.success("동행 완료", null));
    }
}