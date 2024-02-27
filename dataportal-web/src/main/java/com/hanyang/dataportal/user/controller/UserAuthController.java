package com.hanyang.dataportal.user.controller;

import com.hanyang.dataportal.core.jwt.dto.TokenDto;
import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.user.dto.req.*;
import com.hanyang.dataportal.user.dto.res.ResCodeDto;
import com.hanyang.dataportal.user.dto.res.ResUserDto;
import com.hanyang.dataportal.user.service.EmailService;
import com.hanyang.dataportal.user.service.UserLoginService;
import com.hanyang.dataportal.user.service.UserSignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "유저 기존 비밀번호 확인")
    @GetMapping("/password/check")
    public ResponseEntity<ApiResponse<?>> passwordCheck(@AuthenticationPrincipal UserDetails userDetail, @RequestBody ReqPasswordDto reqPasswordDto){
        userLoginService.passwordCheck(userDetail,reqPasswordDto);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @Operation(summary = "유저 비밀번호 변경")
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<?>> passwordChange(@AuthenticationPrincipal UserDetails userDetail,@RequestBody ReqPasswordDto reqPasswordDto){
        userLoginService.changePassword(userDetail,reqPasswordDto.getPassword());
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @Operation(summary = "유저 비밀번호 분실시 임시 비밀번호 발급")
    @GetMapping("/password")
    public ResponseEntity<ApiResponse<?>> passwordChange(@AuthenticationPrincipal UserDetails userDetail){
        userLoginService.findPassword(userDetail);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
