package mjc.capstone.joinus.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT"),
    EMAIL_NOT_VERIFIED(HttpStatus.FORBIDDEN, "EMAIL_NOT_VERIFIED"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "DUPLICATE_EMAIL"),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "DUPLICATE_NICKNAME"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR"),
    IMAGE_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    INVALID_IMAGE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "INVALID_IMAGE"),
    NOT_MEMBER_FOUND(HttpStatus.NOT_FOUND, "NOT_MEMBER_FOUND");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus staus, String internalError) {
        this.status = staus;
        this.message = internalError;
    }
}