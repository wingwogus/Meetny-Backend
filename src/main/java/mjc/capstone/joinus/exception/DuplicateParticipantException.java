package mjc.capstone.joinus.exception;

public class DuplicateParticipantException extends BusinessException {
    public DuplicateParticipantException() {
        super(ErrorCode.DUPLICATE_PARTICIPANT, "동행자가 이미 존재합니다");
    }
}
