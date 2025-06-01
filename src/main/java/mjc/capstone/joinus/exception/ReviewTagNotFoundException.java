package mjc.capstone.joinus.exception;

public class ReviewTagNotFoundException extends BusinessException{
    public ReviewTagNotFoundException() {
        super(ErrorCode.NOT_FOUND_REVIEW_TAG, "매너 태그를 찾을 수 없습니다.");
    }
}
