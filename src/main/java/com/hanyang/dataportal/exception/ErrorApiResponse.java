package com.hanyang.dataportal.exception;

import lombok.Getter;

@Getter
public class ErrorApiResponse{

    private final int status;
    private final String msg;

    public ErrorApiResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ErrorApiResponse(ErrorType errorType) {
        this.status = errorType.status;
        this.msg = errorType.msg;
    }
}
