package com.hanyang.dataportal.exception;

public enum ErrorType {
    DUPLICATE_ID(400,"중복되는 ID가 존재합니다"),
    JWT_UN_SUPPORTED(401,"지원하지 않는 형식의 토큰입니다"),
    JWT_EXPIRED(401,"토큰 유효기간이 지났습니다"),
    WRONG_PASSWORD(403,"잘못된 비밀번호 입니다"),
    ACCESS_DENIED(403,"접근 권한이 없습니다");
    public final int status;
    public final String msg;

    ErrorType(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}

