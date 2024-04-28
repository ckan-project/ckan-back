package com.hanyang.dataportal.user.domain;

public enum Provider {
    KAKAO("kakao");

    private final String value;

    Provider(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
