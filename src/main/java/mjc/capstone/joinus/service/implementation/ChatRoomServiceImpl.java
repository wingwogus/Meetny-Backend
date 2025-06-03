package mjc.capstone.joinus.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import mjc.capstone.joinus.dto.chat.ChatRoomDto;
import mjc.capstone.joinus.exception.*;
import mjc.capstone.joinus.repository.ChatRoomRepository;
import mjc.capstone.joinus.repository.MemberRepository;
import mjc.capstone.joinus.repository.PostRepository;
import mjc.capstone.joinus.service.inf.ChatRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostServiceImpl postService;

    @Transactional(readOnly = true)
    @Override
    public List<ChatRoomDto> chatRoomList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        List<ChatRoom> chatRoomList = chatRoomRepository.findWithPostAndAuthorAndMember(member).
                orElseThrow(NotFoundChatRoomException::new);

        return chatRoomList.stream()
                .map(ChatRoomDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ChatRoomDto findOrCreateRoom(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        ChatRoom chatRoom = chatRoomRepository.findByPostAndMember(post, member)
                .orElseGet(() -> {
                    ChatRoom newRoom = ChatRoom.builder()
                            .roomId(UUID.randomUUID().toString())
                            .post(post)
                            .member(member)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });


        return new ChatRoomDto(chatRoom);
    }

    @Override
    public void completeRoom(String roomId, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        postService.validateAuth(chatRoom.getPost(), member);

        postService.addParticipant(chatRoom.getPost().getId(), chatRoom.getMember().getId());
    }
}
