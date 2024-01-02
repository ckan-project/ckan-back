package com.hanyang.dataportal.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private static final String SUCCESS_MESSAGE = "성공";
    private static final int SUCCESS_STATUS = 200;
    private boolean success;
    private int status;
    private String msg;
    private T result;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ApiResponse(ErrorCode errorCode) {
        this.success = false;
        this.status = errorCode.getStatus();
        this.msg = errorCode.getMessage();
    }
    public static <T> ApiResponse<T> successResponse(T data) {
        return new ApiResponse<>(true,200,SUCCESS_MESSAGE, data);
    }

    public static ApiResponse<?> successResponseNoContent() {
        return new ApiResponse<>(true,SUCCESS_STATUS,SUCCESS_MESSAGE, null);
    }

    public static ApiResponse<?> errorResponse(ErrorCode errorCode){
        return new ApiResponse<>(errorCode);
    }
}
