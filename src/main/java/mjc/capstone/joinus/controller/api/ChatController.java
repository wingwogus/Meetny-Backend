package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.dto.auth.CustomUserDetails;
import mjc.capstone.joinus.dto.chat.ChatRequestDto;
import mjc.capstone.joinus.dto.chat.ChatResponseDto;
import mjc.capstone.joinus.service.inf.ChatRoomService;
import mjc.capstone.joinus.service.inf.ChatService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;
    private final RabbitTemplate rabbitTemplate;
    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";

    @MessageMapping("chat.message.{chatRoomId}")
    public void sendMessage(@Payload ChatRequestDto chat,
                            @DestinationVariable String chatRoomId,
                            Principal principal) {

        ChatResponseDto chatResponseDto = chatService.convertMessage(
                chat,
                principal.getName(),
                chatRoomId);

        rabbitTemplate.convertAndSend(
                CHAT_EXCHANGE_NAME,
                "room." + chatRoomId,
                chatResponseDto);
    }

    //기본적으로 chat.queue가 exchange에 바인딩 되어있기 때문에 모든 메시지 처리
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatResponseDto dto){
        System.out.println("received : " + dto.getMessage());
        chatService.saveChat(dto);
    }
}