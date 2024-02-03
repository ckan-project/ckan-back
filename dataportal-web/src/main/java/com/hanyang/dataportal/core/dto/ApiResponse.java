package com.hanyang.dataportal.core.dto;

import com.hanyang.dataportal.notice.dto.res.ResNoticeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.hanyang.dataportal.core.dto.ResponseMessage.SUCCESS;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String msg;
    private T result;

    public ApiResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, SUCCESS, data);
    }

    public static ApiResponse<?> fail(String message) {
        return new ApiResponse<>(false, message);
    }

    public static ApiResponse<List<ResNoticeDto>> error(String noticeNotFound) {
        return new ApiResponse<>(false,noticeNotFound);
    }
}

