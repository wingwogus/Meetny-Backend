package mjc.capstone.joinus.exception;

public class ReviewNotFoundException extends BusinessException{
    public ReviewNotFoundException() {
        super(ErrorCode.REVIEW_NOT_FOUND, "존재하지 않는 리뷰입니다");
    }
}
