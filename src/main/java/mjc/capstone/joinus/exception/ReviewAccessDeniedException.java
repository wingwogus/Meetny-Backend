package mjc.capstone.joinus.exception;

public class ReviewAccessDeniedException extends BusinessException {

    public ReviewAccessDeniedException(String message) {
        super(ErrorCode.REVIEW_ACCESS_DENIED, message);
    }
}