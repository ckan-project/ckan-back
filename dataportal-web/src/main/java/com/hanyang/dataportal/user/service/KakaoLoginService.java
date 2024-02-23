package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.user.dto.res.ResKakaoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoLoginService {
    @Value("${kakao.rest_api_key}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @Value("${kakao.token_url}")
    private String token_url;

    /**
     * 액세스 토큰을 발급받는 메서드
     * @param code redirect로 넘겨받은 인가코드
     * @return
     */
    public String getAccessToken(String code) {
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
        if (response == null) {
            throw new RuntimeException(token_url + ": POST 요청 응답이 존재하지 않습니다.");
        }

        return response.getAccess_token();
    }
}
