package com.hanyang.dataportal.user.controller;

import com.hanyang.dataportal.core.jwt.dto.TokenDto;
import com.hanyang.dataportal.core.global.reponse.ApiResponse;
import com.hanyang.dataportal.user.dto.req.ReqCodeDto;
import com.hanyang.dataportal.user.dto.req.ReqEmailDto;
import com.hanyang.dataportal.user.dto.req.ReqLoginDto;
import com.hanyang.dataportal.user.dto.req.ReqSignupDto;
import com.hanyang.dataportal.user.dto.res.ResCodeDto;
import com.hanyang.dataportal.user.dto.res.ResUserDto;
import com.hanyang.dataportal.user.service.EmailService;
import com.hanyang.dataportal.user.service.UserLoginService;
import com.hanyang.dataportal.user.service.UserSignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 권한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAuthController {
    private final UserSignupService userSignupService;
    private final UserLoginService userLoginService;
    private final EmailService emailService;
    @Operation(summary = "이메일로 인증 번호 받기")
    @PostMapping("/email")
    public ResponseEntity<ApiResponse<?>> email(@RequestBody ReqEmailDto reqSignupDto){
        return ResponseEntity.ok(ApiResponse.ok(new ResCodeDto(emailService.joinEmail(reqSignupDto.getEmail()))));
    }

    @Operation(summary = "인증 번호 인증")
    @PostMapping("/code")
    public ResponseEntity<ApiResponse<?>> codeCheck(@RequestBody ReqCodeDto reqCodeDto){
        emailService.checkCode(reqCodeDto);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @Operation(summary = "유저 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<ResUserDto>> signup(@RequestBody ReqSignupDto reqSignupDto){
        return ResponseEntity.ok(ApiResponse.ok(new ResUserDto(userSignupService.signUp(reqSignupDto))));
    }
    @Operation(summary = "유저 로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenDto>> login(@RequestBody ReqLoginDto reqLoginDto){
        return ResponseEntity.ok(ApiResponse.ok(userLoginService.login(reqLoginDto)));
    }
}
