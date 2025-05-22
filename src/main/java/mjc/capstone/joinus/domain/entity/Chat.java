package mjc.capstone.joinus.domain.entity;

import jakarta.persistence.Id;
import lombok.Data;
import mjc.capstone.joinus.dto.chat.ChatDto;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "chat")
public class Chat {
    @Id
    private String id;
    private ChatDto.MessageType type; // 메시지 타입
    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
    private String time; // 채팅 발송 시간간
}
