package mjc.capstone.joinus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    @Schema(description = "요청 성공 여부", example = "true", required = true)
    private boolean success;

    @Schema(description = "응답 메시지", example = "OK", required = true)
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    // 정적 팩토리 메서드 (편리하게 만들기)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "OK", data);
    }
    public static <T> ApiResponse<T> success(String msg, T data) {
        return new ApiResponse<>(true, msg, data);
    }
    public static <T> ApiResponse<T> error(String msg) {
        return new ApiResponse<>(false, msg, null);
    }
}