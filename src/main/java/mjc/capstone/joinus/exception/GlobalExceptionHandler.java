package mjc.capstone.joinus.exception;

import mjc.capstone.joinus.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 5) 나머지 비즈니스 예외 (fallback)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException ex) {
        // BusinessException 내부에 ErrorCode가 있다면
        HttpStatus status = ex.getErrorCode().getStatus();
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(body);
    }

    // 6) Validation 오류 (DTO @Valid 실패)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("잘못된 요청입니다.");
        ApiResponse<Void> body = ApiResponse.error(message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    // 7) 그 외 시스템 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(Exception ex) {
        ex.printStackTrace(); // 로그 남기기
        ApiResponse<Void> body = ApiResponse.error("서버 오류가 발생했습니다: " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        ApiResponse<Void> body = ApiResponse.error("아이디와 비밀번호를 확인해주세요");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }
}
