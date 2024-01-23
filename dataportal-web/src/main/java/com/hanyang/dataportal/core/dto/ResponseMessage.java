package com.hanyang.dataportal.core.dto;

public class ResponseMessage {
    public static String SUCCESS ="ok";
    public static String NOT_EXIST_DATASET ="해당 데이터셋은 존재하지 않습니다";
    public static String NOT_EXIST_USER ="해당 유저는 존재하지 않습니다";
    public static String DUPLICATE_EMAIL ="중복되는 이메일이 존재합니다";
    public static String INVALID_JWT ="유효하지 않은 토큰입니다";
    public static String WRONG_PASSWORD ="잘못된 비밀번호 입니다";
    public static String ACCESS_DENIED ="접근 권한이 없습니다";
    public static String UN_AUTHORIZED ="인증되지 않은 사용자 입니다";
}

