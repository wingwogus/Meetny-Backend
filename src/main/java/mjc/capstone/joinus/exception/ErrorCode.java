package mjc.capstone.joinus.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_NOT_FOUND"),
    REVIEW_TAGET_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_TAG_NOT_FOUND"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT"),
    MISSING_REVIEW_TAG(HttpStatus.BAD_REQUEST, "MISSING_REVIEW_TAG"),
    EMAIL_NOT_VERIFIED(HttpStatus.FORBIDDEN, "EMAIL_NOT_VERIFIED"),
    DUPLICATE_REVIEW(HttpStatus.CONFLICT, "DUPLICATE_REVIEW"),
    DUPLICATE_REVIEW_TAG(HttpStatus.CONFLICT, "DUPLICATE_REVIEW_TAG"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "DUPLICATE_EMAIL"),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "DUPLICATE_NICKNAME"),
    DUPLICATE_PARTICIPANT(HttpStatus.CONFLICT, "DUPLICATE_PARTICIPANT"),
    REVIEW_ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "REVIEW_ACCESS_DENIED"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus staus, String internalError) {
        this.status = staus;
        this.message = internalError;
    }
}