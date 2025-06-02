package mjc.capstone.joinus.exception;

public class NotFoundChatRoomException extends BusinessException {
    public NotFoundChatRoomException() {
        super(ErrorCode.NOT_FOUND_CHATROOM, "채팅룸을 찾지 못 했습니다");
    }
}
