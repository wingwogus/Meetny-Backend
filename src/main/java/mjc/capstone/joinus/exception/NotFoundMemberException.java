package mjc.capstone.joinus.exception;

public class NotFoundMemberException extends BusinessException {
    public NotFoundMemberException() {
        super(ErrorCode.NOT_FOUND_MEMBER, "멤버를 찾을 수 없습니다.");
    }
}
