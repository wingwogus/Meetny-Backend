package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.dto.chat.ChatRoomDto;
import mjc.capstone.joinus.service.inf.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {
    private final ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/")
    public ResponseEntity<ResultResponse> chatRoomList(){
        List<ChatRoomDto> chatRoomDTOS = chatService.chatRoomList();
        return ResponseEntity.ok(ResultResponse.of("GET_CHATLIST_SUCCESS", chatRoomDTOS));
    }

    // 채팅방 생성
    @PostMapping("/{roomName}")
    public ResponseEntity<ResultResponse> createRoom(@PathVariable("roomName") String name) {
        ChatRoomDto room = chatService.createRoom(name);
        return ResponseEntity.ok(ResultResponse.of("CREATE_CHATROOM_SUCCESS",room.getRoomId()));
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/userlist")
    public ResponseEntity<ResultResponse> userList(String roomId) {
        List<String> userList = chatService.userList(roomId);
        return ResponseEntity.ok(ResultResponse.of("GET_CHAT_USERLIST_SUCCESS",userList));
    }
}