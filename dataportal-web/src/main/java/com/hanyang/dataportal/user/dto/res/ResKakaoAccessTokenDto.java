package com.hanyang.dataportal.user.dto.res;

import lombok.Data;

@Data
public class ResKakaoDto {
    private final String token_type = "bearer";

    private String access_token;

    private String id_token;

    // 액세스 토큰과 ID 토큰의 만료 시간(초)
    private Integer expires_in;

    private String refresh_token;

    // 리프레시 토큰 만료 시간(초)
    private Integer refresh_token_expires_in;

    private String scope;
}
