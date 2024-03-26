package com.hanyang.dataportal.core.jwt.component;

import com.hanyang.dataportal.core.jwt.dto.TokenDto;
import com.hanyang.dataportal.user.service.RedisService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtSecretKey jwtSecretKey;
    private final RedisService redisService;

    @Value("${jwt.expire.refresh}")
    private Long refreshExpire;
    @Value("${jwt.expire.access}")
    private Long accessExpire;

    // JWT 토큰 생성
    private String generateToken(final Authentication authentication, final Long expiredInMillisecond) {
        // role 가져오기
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        final long now = (new Date()).getTime();
        final Date accessTokenExpiresIn = new Date(now + expiredInMillisecond);

        // Access Token 생성
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(jwtSecretKey.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * refresh token 생성 메서드
     * @param username
     */
     private String generateRefreshToken(final String username, final Long expiredInMillisecond) {
        final String refreshToken = UUID.randomUUID().toString();
        redisService.setCode(username, refreshToken, expiredInMillisecond);
        return refreshToken;
    }

    /**
     * 액세스 토큰과 리프레시 토큰을 새로 발급하는 메서드
     * @param authentication
     * @return
     */
    public TokenDto generateLoginToken(Authentication authentication) {
        final String accessToken = generateToken(authentication, accessExpire);
        final String refreshToken = generateRefreshToken(authentication.getName(), refreshExpire);
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}