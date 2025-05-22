package mjc.capstone.joinus.dto.chat;

import lombok.Data;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChatRoomDto {
    private String roomId; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private long userCount; // 채팅방 인원수
    private List<String> userList = new ArrayList<>();

    public ChatRoomDto(ChatRoom chatRoom){
        this.roomId = chatRoom.getRoomId();
        this.roomName = chatRoom.getRoomName();
        this.userCount = chatRoom.getUserCount();
        if (!(chatRoom.getUserCount() == 0)) {
            this.userList = chatRoom.getUserList().stream().map(Member::getNickname).collect(Collectors.toList());
        }
    }
}


