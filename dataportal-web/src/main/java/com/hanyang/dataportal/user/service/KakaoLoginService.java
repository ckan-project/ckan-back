package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.user.dto.res.ResKakaoAccessTokenDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoLoginService {
    @Value("${kakao.rest_api_key}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @Value("${kakao.token_url}")
    private String token_url;

    @Value("${kakao.userInfo_url}")
    private String userInfo_url;

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
        ResKakaoAccessTokenDto response = webClient.post()
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData(formData))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(ResKakaoAccessTokenDto.class)
                .block();

        System.out.println(response);
        if (response == null) {
            throw new RuntimeException(token_url + ": POST 요청 응답이 존재하지 않습니다.");
        }

        return response.getAccess_token();
    }

    /**
     *
     * @param accessToken 발급받은 액세스 토큰
     * @return
     */
    public Map<String, Object> getUserInfo(String accessToken) {
        Map<String, Object> result = new HashMap<>();
        try {
            WebClient webClient = WebClient.create(userInfo_url);
            String response = webClient.get()
                    .headers(httpHeaders -> {
                        httpHeaders.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", accessToken));
                        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
                    })
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println(response);

            // parsing response to JSON
            JSONParser parser = new JSONParser();
            JSONObject responseObj = (JSONObject) parser.parse(response);
            JSONObject properties = (JSONObject) responseObj.get("properties");
            JSONObject kakao_account = (JSONObject) responseObj.get("kakao_account");

            // parsing required values
            String userId = responseObj.get("id").toString();
            String name = properties.get("nickname").toString();
            String email = kakao_account.get("email").toString();

            // 리턴값 구성
            result.put("userId", userId);
            result.put("email", email);
            result.put("name", name);
        } catch (ParseException e) {
            throw new RuntimeException("프로퍼티가 존재하지 않습니다.");
        }

        return result;
    }
}
