package mjc.capstone.joinus.exception;

public class SelfJoinNotAllowedException extends BusinessException {

    public SelfJoinNotAllowedException() {
        super(ErrorCode.SELF_JOIN_NOT_ALLOWED, "자신의 동행에 참여할 수 없습니다.");
    }
}
