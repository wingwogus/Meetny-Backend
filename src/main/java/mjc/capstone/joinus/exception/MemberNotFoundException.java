package mjc.capstone.joinus.exception;

public class MemberNotFoundException extends BusinessException{

    public MemberNotFoundException(){
        super(ErrorCode.MEMBER_NOT_FOUND, "유효하지 않은 사용자입니다");
    }
}
