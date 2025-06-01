package mjc.capstone.joinus.exception;

public class ReviewNotFoundException extends BusinessException{
    public ReviewNotFoundException() {
        super(ErrorCode.NOT_FOUND_REVIEW, "존재하지 않는 리뷰입니다");
    }
}
