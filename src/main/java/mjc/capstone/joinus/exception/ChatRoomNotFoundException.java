package mjc.capstone.joinus.exception;

public class ChatRoomNotFoundException extends BusinessException {
    public ChatRoomNotFoundException(String roomId) {
        super(ErrorCode.CHATROOM_NOT_FOUND, roomId + " 채팅룸을 찾지 못 했습니다");
    }
}
