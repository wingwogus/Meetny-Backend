package mjc.capstone.joinus.exception;

public class InvalidImageException extends BusinessException {
    public InvalidImageException() {
        super(ErrorCode.INVALID_IMAGE, "지원하지 않는 이미지 형식입니다. (png, jpg, jpeg만 가능)");
    }
}
