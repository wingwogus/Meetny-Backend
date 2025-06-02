package mjc.capstone.joinus.exception;

public class NotFoundPostException extends BusinessException {
    public NotFoundPostException() {
        super(ErrorCode.NOT_FOUND_POST, "포스트를 찾지 못 했습니다");
    }
}
