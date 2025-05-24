package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.dto.chat.ChatRequestDto;
import mjc.capstone.joinus.dto.chat.ChatResponseDto;

public interface ChatService {
    ChatResponseDto convertMessage(ChatRequestDto dto, String memberId, String roomId);

    void saveChat(ChatResponseDto dto);
}
