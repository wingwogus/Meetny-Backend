package mjc.capstone.joinus.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mjc.capstone.joinus.domain.entity.Chat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {

    @Schema(description = "방 번호", example = "락, 콘서트")
    private String roomId;

    @Schema(description = "채팅을 보낸 사람", example = "user1")
    private String sender;

    @Schema(description = "메시지", example = "안녕하세요")
    private String message;

    @Schema(description = "채팅 발송 시간", example = "2024.10.11")
    private String sendAt;
}
