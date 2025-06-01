package mjc.capstone.joinus.controller.api;

import lombok.Data;

@Data
public class ResultResponse<T> {
    private String status;
    private T data;

    private ResultResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ResultResponse<T> of(String status, T data) {
        return new ResultResponse<>(status, data);
    }
}