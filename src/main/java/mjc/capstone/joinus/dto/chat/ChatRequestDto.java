package mjc.capstone.joinus.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mjc.capstone.joinus.domain.entity.Chat;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;

import java.time.LocalDateTime;

@Data
public class ChatRequestDto {

    @Schema(description = "전송할 메시지", example = "안녕하세요")
    private String message;
}