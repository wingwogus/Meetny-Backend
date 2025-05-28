package mjc.capstone.joinus.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.dto.ApiResponse;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import mjc.capstone.joinus.dto.chat.ChatRoomDto;
import mjc.capstone.joinus.service.inf.ChatRoomService;
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

    // 채팅 리스트 화면
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ChatRoomDto>>> chatRoomList(
            @AuthenticationPrincipal CustomUserDetails userDetails){

        Long memberId = userDetails.getMember().getId();

        List<ChatRoomDto> chatRoomDtos = chatRoomService.chatRoomList(memberId);
        return ResponseEntity.ok(ApiResponse.success("채팅창 조회 성공", chatRoomDtos));
    }

    // 채팅방 입장
    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<ChatRoomDto>> findOrCreateRoom(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();

        ChatRoomDto room = chatRoomService.findOrCreateRoom(postId, memberId);
        return ResponseEntity.ok(ApiResponse.success("채팅방 입장 성공", room));
    }

    @PostMapping("/{roomId}/complete")
    public ResponseEntity<ApiResponse<Void>> completeRoom(
            @PathVariable String roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();

        chatRoomService.completeRoom(roomId, memberId);

        return ResponseEntity.ok(ApiResponse.success("동행 완료", null));
    }
}