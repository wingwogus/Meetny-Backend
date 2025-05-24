package mjc.capstone.joinus.exception;

public class NotFoundMemberException extends BusinessException {
    public NotFoundMemberException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
