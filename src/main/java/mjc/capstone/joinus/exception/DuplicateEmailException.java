package mjc.capstone.joinus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException(String email) {
        super(ErrorCode.DUPLICATE_EMAIL, email + "은 이미 존재하는 이메일입니다");
    }
}
