package com.hanyang.dataportal.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    /**
     * refresh token 쿠키를 리턴하는 메서드
     * @param refreshToken
     * @return set-cookie 헤더에 넣을 쿠키
     */
    public ResponseCookie generateRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                //TODO: https 배포 이후 true로 변경
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .build();
    }
}
