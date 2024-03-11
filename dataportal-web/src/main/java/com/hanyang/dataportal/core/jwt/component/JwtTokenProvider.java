package com.hanyang.dataportal.core.jwt.component;

import com.hanyang.dataportal.core.jwt.dto.TokenDto;
import com.hanyang.dataportal.user.service.RedisService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

    // JWT 토큰 생성
    public TokenDto generateToken(Authentication authentication) {
        // role 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        long millisecondsInADay = 1000L * 60L * 30L;
        Date accessTokenExpiresIn = new Date(now + millisecondsInADay);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(jwtSecretKey.getKey(), SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = UUID.randomUUID().toString();
        // Redis 저장
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        redisService.setCode(username, refreshToken, refreshExpire);

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}