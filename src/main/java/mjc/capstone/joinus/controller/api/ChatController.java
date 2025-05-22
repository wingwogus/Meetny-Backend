package mjc.capstone.joinus.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.dto.chat.ChatDto;
import mjc.capstone.joinus.service.inf.ChatService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;
    private final RabbitTemplate rabbitTemplate;

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";

    // /pub/chat.message.{roomId} 로 요청하면 브로커를 통해 처리
    // /exchange/chat.exchange/room.{roomId} 를 구독한 클라이언트에 메시지가 전송된다.
    @MessageMapping("chat.enter.{chatRoomId}")
    public void enterUser(@Payload ChatDto chat, @DestinationVariable String chatRoomId) {
        chat.setTime(LocalDateTime.now().toString());
        chat.setMessage(chat.getSender() + " 님 입장!!");
        chatService.addUser(chatRoomId, chat.getSender());
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);

    }

    @MessageMapping("chat.message.{chatRoomId}")
    public void sendMessage(@Payload ChatDto chat, @DestinationVariable String chatRoomId) {
        chat.setTime(LocalDateTime.now().toString());
        chat.setMessage(chat.getMessage());
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);

    }

    //기본적으로 chat.queue가 exchange에 바인딩 되어있기 때문에 모든 메시지 처리
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatDto chatDTO){
        System.out.println("received : " + chatDTO.getMessage());
    }

}