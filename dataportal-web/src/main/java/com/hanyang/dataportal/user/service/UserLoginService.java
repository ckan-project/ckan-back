package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.exception.TokenExpiredException;
import com.hanyang.dataportal.core.exception.UnAuthenticationException;
import com.hanyang.dataportal.core.jwt.component.JwtTokenProvider;
import com.hanyang.dataportal.core.jwt.component.JwtTokenResolver;
import com.hanyang.dataportal.core.jwt.component.JwtTokenValidator;
import com.hanyang.dataportal.core.jwt.dto.TokenDto;
import com.hanyang.dataportal.core.response.ResponseMessage;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.dto.req.ReqLoginDto;
import com.hanyang.dataportal.user.dto.req.ReqPasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenValidator jwtTokenValidator;
    private final JwtTokenResolver jwtTokenResolver;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public TokenDto login(ReqLoginDto reqLoginDto) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(reqLoginDto.getEmail(), reqLoginDto.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // JWT 토큰 생성
        return jwtTokenProvider.generateLoginToken(authentication, reqLoginDto.getAutoLogin());
    }

    /**
     * 액세스 토큰(+ 리프레시 토큰)을 재발급하는 메서드
     * @param accessToken
     * @param refreshToken
     * @return
     * @throws TokenExpiredException
     */
    public TokenDto reissueToken(final String accessToken, final String refreshToken) throws TokenExpiredException {
        final Authentication authentication = jwtTokenResolver.getAuthentication(accessToken);
        final boolean autoLogin = jwtTokenResolver.getAutoLogin(accessToken);
        if (jwtTokenValidator.validateRefreshToken(authentication.getName(), refreshToken)) {
            return jwtTokenProvider.generateLoginToken(authentication, autoLogin);
        }
        throw new TokenExpiredException(ResponseMessage.REFRESH_EXPIRED);
    }

    /**
     * refresh token 쿠키를 리턴하는 메서드
     * @param refreshToken
     * @param accessToken
     * @return
     */
    public ResponseCookie generateRefreshCookie(final String refreshToken, final String accessToken) {
        return jwtTokenProvider.generateRefreshCookie(refreshToken, jwtTokenResolver.getAutoLogin(accessToken));
    }

    public void passwordCheck(UserDetails userDetails, ReqPasswordDto reqPasswordDto){
        User user = userService.findByEmail(userDetails.getUsername());
        //일치하면
        if(passwordEncoder.matches(reqPasswordDto.getPassword(),user.getPassword())) {
            return;
        }
        throw new UnAuthenticationException(ResponseMessage.WRONG_PASSWORD);
    }
    public void changePassword(UserDetails userDetails,String newPassword){
        User user = userService.findByEmail(userDetails.getUsername());
        user.changePassword(passwordEncoder.encode(newPassword));
    }

    public void findPassword(UserDetails userDetails){
        User user = userService.findByEmail(userDetails.getUsername());
        String temporaryPassword = emailService.temporaryPasswordEmail(user.getPassword());
        changePassword(userDetails,temporaryPassword);
    }
}
