package com.hanyang.dataportal.user.controller;

import com.hanyang.dataportal.user.service.KakaoLoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "kakao 로그인 API")
@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoLoginService kakaoLoginService;
    @GetMapping("login/oauth2/kakao")
    public void kakaoLogin(@RequestParam String code) {
        String accessToken = kakaoLoginService.getAccessToken(code);
        System.out.println("accessToken: \n"+accessToken);
    }
}
