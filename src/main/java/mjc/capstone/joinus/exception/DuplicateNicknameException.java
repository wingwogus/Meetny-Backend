package mjc.capstone.joinus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateNicknameException extends BusinessException {

    public DuplicateNicknameException(String nickname) {
        super(ErrorCode.DUPLICATE_NICKNAME, nickname + "은 이미 존재하는 닉네임입니다.");
    }
}
