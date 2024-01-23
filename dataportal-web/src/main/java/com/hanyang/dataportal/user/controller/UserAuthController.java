package com.hanyang.dataportal.user.controller;

import com.hanyang.dataportal.core.dto.ApiResponse;
import com.hanyang.dataportal.core.jwt.dto.TokenDto;
import com.hanyang.dataportal.user.dto.req.ReqLoginDto;
import com.hanyang.dataportal.user.dto.req.ReqSignupDto;
import com.hanyang.dataportal.user.dto.res.ResUserDto;
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
