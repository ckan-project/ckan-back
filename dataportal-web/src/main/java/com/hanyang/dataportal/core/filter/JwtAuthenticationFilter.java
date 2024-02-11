package com.hanyang.dataportal.core.filter;

import com.hanyang.dataportal.core.component.ApiResponseBuilder;
import com.hanyang.dataportal.core.jwt.component.JwtTokenResolver;
import com.hanyang.dataportal.core.jwt.component.JwtTokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.hanyang.dataportal.core.global.reponse.ResponseMessage.INVALID_JWT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenResolver jwtTokenResolver;
    private final JwtTokenValidator jwtTokenValidator;
    private final ApiResponseBuilder apiResponseBuilder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Request Header에서 JWT 토큰 추출
        String token = jwtTokenResolver.extractToken(request);

        // 2. validateToken 으로 토큰 유효성 검사
        if (token != null) {
            if (jwtTokenValidator.validateToken(token)){
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가져와 SecurityContext에 저장
                Authentication authentication = jwtTokenResolver.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
            else{
                apiResponseBuilder.buildErrorResponse((HttpServletResponse) response, BAD_REQUEST, INVALID_JWT);
            }
        }
        else{
            filterChain.doFilter(request, response);
        }
    }
}
