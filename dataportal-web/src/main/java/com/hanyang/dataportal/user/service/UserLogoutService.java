package com.hanyang.dataportal.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLogoutService {
    private final RedisService redisService;

    /**
     * 로그인 유저를 로그아웃 처리하는 메서드 (redis 내 해당 유저의 칼럼 삭제)
     * @param userDetails
     */
    public void logout(UserDetails userDetails) {
        redisService.deleteCode(userDetails.getUsername());
    }
}
