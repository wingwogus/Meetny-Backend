package mjc.capstone.joinus.service.inf;

import mjc.capstone.joinus.dto.chat.ChatRoomDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatService {
    List<ChatRoomDto> chatRoomList();

    @Transactional
    ChatRoomDto createRoom(String roomName);

    List<String> userList(String roomId);

    @Transactional
    Long addUser(String roomId, String userName);

    @Transactional
    void delUser(String roomId, String userName);
}
