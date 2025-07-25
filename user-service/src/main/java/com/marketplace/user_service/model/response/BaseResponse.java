package com.marketplace.user_service.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private int code;
    private String message;
    private T result;

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(200, "Success", data);
    }

    public static <T> BaseResponse<T> ok(String message) {
        return new BaseResponse<>(200, message, null);
    }

    public static <T> BaseResponse<T> ok(T data, String message) {
        return new BaseResponse<>(200, message, data);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(400, message, null);
    }

    public static <T> BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, message, null);
    }
}
