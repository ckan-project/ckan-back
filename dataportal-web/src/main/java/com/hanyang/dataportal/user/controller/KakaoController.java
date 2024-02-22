package com.hanyang.dataportal.user.controller;

import com.hanyang.dataportal.user.dto.res.ResKakaoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Tag(name = "kakao 로그인 API")
@RestController
public class KakaoController {
    @Value("${kakao.rest_api_key}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @Value("${kakao.token_url}")
    private String token_url;

    @GetMapping("login/oauth2/kakao")
    public void getAccessToken(@RequestParam String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", "authorization_code");
        formData.add("client_id", client_id);
        formData.add("redirect_uri", redirect_uri);
        formData.add("code", code);

        WebClient webClient = WebClient.create(token_url);
        ResKakaoDto response = webClient.post()
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData(formData))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(ResKakaoDto.class)
                .block();

        System.out.println(response);
        // TODO: user 정보 확인 -> DB 저장 or 유저 검증
    }
}
