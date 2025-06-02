package mjc.capstone.joinus.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.Chat;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.dto.chat.ChatRequestDto;
import mjc.capstone.joinus.dto.chat.ChatResponseDto;
import mjc.capstone.joinus.exception.NotFoundChatRoomException;
import mjc.capstone.joinus.repository.ChatRepository;
import mjc.capstone.joinus.repository.ChatRoomRepository;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.inf.ChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Transactional(readOnly = true)
    @Override
    public ChatResponseDto convertMessage(ChatRequestDto dto, String username, String roomId) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("멤버를 찾을 수 없습니다"));

        return ChatResponseDto.builder()
                .message(dto.getMessage())
                .sender(member.getNickname())
                .roomId(roomId)
                .sendAt(LocalDateTime.now().toString())
                .build();
    }

    @Override
    public void saveChat(ChatResponseDto dto) {
        Member member = memberRepository.findByNickname(dto.getSender())
                .orElseThrow(() -> new EntityNotFoundException("멤버를 찾을 수 없습니다"));

        ChatRoom chatRoom = chatRoomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다"));

        Chat chat = Chat.builder()
                .sender(member)
                .room(chatRoom)
                .message(dto.getMessage())
                .time(LocalDateTime.parse(dto.getSendAt()))
                .build();

        chatRepository.save(chat);
    }

    @Override
    public List<ChatResponseDto> getChatList(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);

        return chatRepository.findByRoom(chatRoom).stream()
                .map(chat -> ChatResponseDto.builder()
                    .message(chat.getMessage())
                    .sender(chat.getSender().getNickname())
                    .roomId(roomId)
                    .sendAt(chat.getTime().toString())
                    .build())
                .toList();
    }
}
