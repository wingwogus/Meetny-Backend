package mjc.capstone.joinus.dto.chat;

import lombok.Data;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRoomDto {
    private String roomId; // 채팅방 아이디
    private Long postId;
    private String postTitle;
    private String authorNickname;
    private String userNickname;

    public ChatRoomDto(ChatRoom chatRoom){
        this.roomId = chatRoom.getRoomId();
        Post post = chatRoom.getPost();
        this.postId = post.getId();
        this.postTitle = post.getTitle();
        this.authorNickname = post.getAuthor().getNickname();
        this.userNickname = chatRoom.getMember().getNickname();
    }
}


