package mjc.capstone.joinus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ImageSaveFailedException extends BusinessException {
    public ImageSaveFailedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
