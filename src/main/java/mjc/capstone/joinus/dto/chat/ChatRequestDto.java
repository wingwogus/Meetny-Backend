package mjc.capstone.joinus.dto.chat;

import lombok.Data;
import mjc.capstone.joinus.domain.entity.Chat;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;

import java.time.LocalDateTime;

@Data
public class ChatRequestDto {
    private String message; // 메시지
}