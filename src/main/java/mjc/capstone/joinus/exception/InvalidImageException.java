package mjc.capstone.joinus.exception;

public class InvalidImageException extends BusinessException {
    public InvalidImageException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
