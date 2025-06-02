package mjc.capstone.joinus.exception;

public class DuplicateReviewTagException extends BusinessException{
    public DuplicateReviewTagException() {
        super(ErrorCode.DUPLICATE_REVIEW_TAG, "동일한 태그를 중복으로 선택할 수 없습니다");
    }
}
