package com.hanyang.dataportal.exception.controller;

import com.hanyang.dataportal.exception.ApplicationException;
import com.hanyang.dataportal.exception.CustomException;
import com.hanyang.dataportal.utill.ApiResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.hanyang.dataportal.exception.ErrorType.WRONG_PASSWORD;
import static com.hanyang.dataportal.utill.ApiResponse.errorResponse;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(CustomException.class)
    public ApiResponse<?> handleCustomException(CustomException ex) {
        return errorResponse(ex.getErrorApiResponse());
    }

    @ExceptionHandler(ApplicationException.class)
    public ApiResponse<?> handleApplicationException(ApplicationException ex) {
        return errorResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<?> handleBadCredentialsException(BadCredentialsException ex){
        return errorResponse(WRONG_PASSWORD);
    }
}
