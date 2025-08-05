package mjc.capstone.joinus.exception;

public class WithdrawnMemberException extends BusinessException {

    public WithdrawnMemberException() {
        super(ErrorCode.WITHDRAWN_MEMBER, "탈퇴한 사용자입니다.");
    }
}
