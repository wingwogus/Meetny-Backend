package mjc.capstone.joinus.exception;

public class ReviewTagNotFoundException extends BusinessException{
    public ReviewTagNotFoundException() {
        super(ErrorCode.REVIEW_TAGET_NOT_FOUND, "매너 태그를 찾을 수 없습니다.");
    }
}
