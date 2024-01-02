package com.hanyang.dataportal.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApplicationException extends RuntimeException {
    private final ErrorCode errorCode;
    public ApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
