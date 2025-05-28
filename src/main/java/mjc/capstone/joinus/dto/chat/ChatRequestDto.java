package mjc.capstone.joinus.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChatRequestDto {

    @Schema(description = "전송할 메시지", example = "안녕하세요")
    private String message;
}