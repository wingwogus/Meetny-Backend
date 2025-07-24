package mjc.capstone.joinus.exception;

public class MissingReviewTagException extends BusinessException {
    public MissingReviewTagException() {
      super(ErrorCode.MISSING_REVIEW_TAG, "하나 이상의 매너태그를 선택해주세요");
    }
}
