package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.exception.UnAuthenticationException;
import com.hanyang.dataportal.core.jwt.component.JwtTokenProvider;
import com.hanyang.dataportal.core.jwt.dto.TokenDto;
import com.hanyang.dataportal.core.response.ResponseMessage;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.dto.req.ReqLoginDto;
import com.hanyang.dataportal.user.dto.req.ReqPasswordDto;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public TokenDto login(ReqLoginDto reqLoginDto){
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(reqLoginDto.getEmail(), reqLoginDto.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);
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
