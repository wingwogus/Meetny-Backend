package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.dto.chat.ChatRequestDto;
import mjc.capstone.joinus.dto.chat.ChatResponseDto;
import mjc.capstone.joinus.dto.chat.ChatRoomDto;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoomDto> chatRoomList(Long memberId);

    ChatRoomDto findOrCreateRoom(Long postId, Long memberId);

    void completeRoom(String roomId, Long memberId);
}
