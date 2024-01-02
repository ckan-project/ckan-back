package com.hanyang.dataportal.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.hanyang.dataportal.exception.ApiResponse.errorResponse;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ApplicationException.class)
    public ApiResponse<?> handleApplicationException(ApplicationException ex) {
        return errorResponse(ex.getErrorCode());
    }
}
