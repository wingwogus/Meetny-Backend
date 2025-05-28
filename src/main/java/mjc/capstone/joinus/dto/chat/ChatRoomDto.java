package mjc.capstone.joinus.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRoomDto {

    @Schema(description = "채팅방 아이디", example = "락, 콘서트")
    private String roomId; // 채팅방 아이디

    @Schema(description = "포스트 아이디", example = "1")
    private Long postId;

    @Schema(description = "포스트 제목", example = "콘서트 동행 구해요")
    private String postTitle;

    @Schema(description = "포스트 저자 닉네임", example = "거인이재현")
    private String authorNickname;

    @Schema(description = "참여자 닉네임", example = "소인이재현")
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


