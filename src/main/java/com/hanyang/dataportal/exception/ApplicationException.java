package com.hanyang.dataportal.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final int status;
    private final String msg;

    public ApplicationException(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
}
