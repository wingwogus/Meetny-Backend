package mjc.capstone.joinus.exception;

public class DuplicateReviewException extends BusinessException{
    public DuplicateReviewException() {
        super(ErrorCode.DUPLICATE_REVIEW, "리뷰가 이미 존재합니다");
    }
}
