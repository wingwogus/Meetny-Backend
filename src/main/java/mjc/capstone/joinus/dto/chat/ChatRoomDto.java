package mjc.capstone.joinus.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mjc.capstone.joinus.domain.entity.Chat;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;

import java.time.LocalDateTime;

@Data
public class ChatRoomDto {

    @Schema(description = "채팅방 아이디", example = "락, 콘서트")
    private String roomId; // 채팅방 아이디

    @Schema(description = "포스트 아이디", example = "1")
    private Long postId;

    @Schema(description = "포스트 제목", example = "콘서트 동행 구해요")
    private String postTitle;

    @Schema(description = "포스트 이미지", example = "포스트 이미지")
    private String postImage;

    @Schema(description = "포스트 저자 닉네임", example = "거인이재현")
    private String authorNickname;

    @Schema(description = "포스트 저자 이미지", example = "img")
    private String authorImage;

    @Schema(description = "참여자 닉네임", example = "소인이재현")
    private String userNickname;

    @Schema(description = "참여자 이미지", example = "img")
    private String userImage;

    @Schema(description = "마지막 채팅", example = "안녕하세요")
    private String latestMessage;

    @Schema(description = "마지막 채팅 시간", example = "2024;11")
    private LocalDateTime latestTime;

    public ChatRoomDto(ChatRoom chatRoom){
        this.roomId = chatRoom.getRoomId();

        Post post = chatRoom.getPost();
        this.postId = post.getId();
        this.postTitle = post.getTitle();
        this.postImage = post.getPhoto();

        Member author = post.getAuthor();
        this.authorNickname = author.getNickname();
        this.authorImage = author.getProfileImg();

        Member user = chatRoom.getMember();
        this.userNickname = user.getNickname();
        this.userImage = user.getProfileImg();

        if (!chatRoom.getChatList().isEmpty()) {
            Chat lastChat = chatRoom.getChatList().getLast();
            this.latestMessage = lastChat.getMessage();
            this.latestTime = lastChat.getTime();
        }
    }
}


