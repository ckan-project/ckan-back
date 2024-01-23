package com.hanyang.dataportal.core.filter;

import com.hanyang.dataportal.core.component.ApiResponseBuilder;
import com.hanyang.dataportal.core.jwt.component.JwtTokenResolver;
import com.hanyang.dataportal.core.jwt.component.JwtTokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.hanyang.dataportal.core.dto.ResponseMessage.INVALID_JWT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@RequiredArgsConstructor
public class CustomGenericFilterBean extends GenericFilterBean {

    private final JwtTokenResolver jwtTokenResolver;
    private final JwtTokenValidator jwtTokenValidator;
    private final ApiResponseBuilder apiResponseBuilder;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 1. Request Header에서 JWT 토큰 추출
        String token = jwtTokenResolver.extractToken((HttpServletRequest) request);

        // 2. validateToken 으로 토큰 유효성 검사
        if (token != null) {
            if (jwtTokenValidator.validateToken(token)){
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가져와 SecurityContext에 저장
                Authentication authentication = jwtTokenResolver.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }
            else{
                apiResponseBuilder.buildErrorResponse((HttpServletResponse) response, BAD_REQUEST, INVALID_JWT);
            }
        }
        else{
            chain.doFilter(request, response);
        }
    }
}
