package mjc.capstone.joinus.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.dto.chat.ChatRoomDto;
import mjc.capstone.joinus.repository.ChatRoomRepository;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.service.inf.ChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ChatRoomDto> chatRoomList() {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        return chatRoomList.stream().map(ChatRoomDto::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ChatRoomDto createRoom(String roomName) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .userCount(0)
                .build();
        chatRoomRepository.save(chatRoom);
        return new ChatRoomDto(chatRoom);
    }

    @Override
    public List<String> userList(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("CHATROOM_NOT_FOUND"));
        return chatRoom.getUserList().stream().map(user -> user.getNickname()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long addUser(String roomId, String userName) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("CHATROOM_NOT_FOUND"));
        Member user = memberRepository.findByNickname(userName).orElseThrow(() -> new EntityNotFoundException("MEMBER_NOT_FOUND"));
        chatRoom.upUserCount();
        chatRoom.addUser(user);

        return user.getId();
    }

    @Transactional
    @Override
    public void delUser(String roomId, String userName) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("CHATROOM_NOT_FOUND"));
        Member user = memberRepository.findByNickname(userName).orElseThrow(() -> new EntityNotFoundException("MEMBER_NOT_FOUND"));
        chatRoom.downUserCount();
        chatRoom.removeUser(user);
    }
}