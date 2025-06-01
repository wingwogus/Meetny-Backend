package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.dto.chat.ChatRequestDto;
import mjc.capstone.joinus.dto.chat.ChatResponseDto;

import java.util.List;

public interface ChatService {
    ChatResponseDto convertMessage(ChatRequestDto dto, String memberId, String roomId);

    void saveChat(ChatResponseDto dto);

    List<ChatResponseDto> getChatList(String roomId);
}
