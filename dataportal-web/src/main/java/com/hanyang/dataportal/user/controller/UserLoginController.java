package com.hanyang.dataportal.user.controller;

import com.hanyang.dataportal.core.jwt.component.AuthorizationExtractor;
import com.hanyang.dataportal.core.jwt.dto.TokenDto;
import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.user.dto.req.ReqLoginDto;
import com.hanyang.dataportal.user.dto.req.ReqOauthDto;
import com.hanyang.dataportal.user.dto.res.ResLoginDto;
import com.hanyang.dataportal.user.service.OauthLoginService;
import com.hanyang.dataportal.user.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/login")
public class UserLoginController {
    private final UserLoginService userLoginService;
    private final OauthLoginService oauthLoginService;

    @Operation(summary = "유저 로그인")
    @PostMapping("")
    public ResponseEntity<ApiResponse<ResLoginDto>> login(@RequestBody ReqLoginDto reqLoginDto){
        final TokenDto tokenDto = userLoginService.login(reqLoginDto);
        final ResponseCookie responseCookie = userLoginService.generateRefreshCookie(tokenDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(ApiResponse.ok(new ResLoginDto(AuthorizationExtractor.AUTH_TYPE, tokenDto.getAccessToken())));
    }

    @Operation(summary = "소셜 로그인")
    @PostMapping("/{provider}")
    public ResponseEntity<ApiResponse<ResLoginDto>> oauthLogin(
            @PathVariable final String provider,
            @RequestBody final ReqOauthDto reqOauthDto
    ) {
        final TokenDto tokenDto = oauthLoginService.login(provider, reqOauthDto.getCode());
        final ResponseCookie responseCookie = userLoginService.generateRefreshCookie(tokenDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(ApiResponse.ok(new ResLoginDto(AuthorizationExtractor.AUTH_TYPE, tokenDto.getAccessToken())));
    }
}
