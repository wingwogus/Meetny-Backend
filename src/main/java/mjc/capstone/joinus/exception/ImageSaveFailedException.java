package mjc.capstone.joinus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ImageSaveFailedException extends BusinessException {
    public ImageSaveFailedException() {
        super(ErrorCode.IMAGE_SAVE_ERROR, "이미지 저장에 실패했습니다.");
    }

}
