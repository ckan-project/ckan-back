package com.hanyang.dataportal.user.controller;

import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.user.dto.req.ReqLoginDto;
import com.hanyang.dataportal.user.dto.req.ReqSignupDto;
import com.hanyang.dataportal.user.service.KakaoLoginService;
import com.hanyang.dataportal.user.service.UserLoginService;
import com.hanyang.dataportal.user.service.UserService;
import com.hanyang.dataportal.user.service.UserSignupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "kakao 로그인 API")
@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoLoginService kakaoLoginService;
    private final UserService userService;
    private final UserLoginService userLoginService;
    private final UserSignupService userSignupService;

    @GetMapping("login/oauth2/kakao")
    public ResponseEntity<ApiResponse<?>> kakaoLogin(@RequestParam String code) {
        try {
            String accessToken = kakaoLoginService.getAccessToken(code);
            Map<String, String> userInfo = kakaoLoginService.getUserInfo(accessToken);
            String userId = userInfo.get("userId");
            // 이미 카카오 연동이 완료된 회원인 경우 -> 로그인 처리
            if (userService.isExistByEmail(userId)) {
                ReqLoginDto reqLoginDto = new ReqLoginDto();
                reqLoginDto.setEmail(userId);
                reqLoginDto.setPassword(userId);
                return ResponseEntity.ok(ApiResponse.ok(userLoginService.login(reqLoginDto)));
            }
            // 카카오 정보로 회원가입 진행
            String name = userInfo.get("name");
            ReqSignupDto reqSignupDto = new ReqSignupDto();
            reqSignupDto.setEmail(userId);
            reqSignupDto.setPassword(userId);
            reqSignupDto.setName(name);
            return ResponseEntity.ok(ApiResponse.ok(userSignupService.signUp(reqSignupDto)));
        } catch (ParseException e) {
            // 카카오 API와 json 파싱 값이 일치하지 않는 경우 -> 5xx error
            return ResponseEntity.internalServerError().build();
        }
    }
}
