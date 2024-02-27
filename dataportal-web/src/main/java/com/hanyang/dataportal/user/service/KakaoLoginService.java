package com.hanyang.dataportal.user.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginService {
    @Value("${kakao.rest_api_key}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @Value("${kakao.token_url}")
    private String token_url;

    @Value("${kakao.userInfo_url}")
    private String userInfo_url;

    private final JSONParser jsonParser = new JSONParser();

    /**
     * 액세스 토큰을 발급받는 메서드
     * @param code redirect로 넘겨받은 인가코드
     * @return
     */
    public String getAccessToken(String code) throws ParseException {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", client_id);
        formData.add("redirect_uri", redirect_uri);
        formData.add("code", code);

        WebClient webClient = WebClient.create(token_url);
        String response = webClient.post()
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData(formData))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class)
                .block();

        JSONObject responseObj = (JSONObject) jsonParser.parse(response);
        return responseObj.get("access_token").toString();
    }

    /**
     *
     * @param accessToken 발급받은 액세스 토큰
     * @return
     */
    public Map<String, String> getUserInfo(String accessToken) throws ParseException {
        Map<String, String> result = new HashMap<>();

        WebClient webClient = WebClient.create(userInfo_url);
        String response = webClient.get()
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", accessToken));
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
                })
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // parsing response to JSON
        JSONObject responseObj = (JSONObject) jsonParser.parse(response);
        JSONObject properties = (JSONObject) responseObj.get("properties");

        // parsing required values
        String userId = responseObj.get("id").toString();
        String name = properties.get("nickname").toString();

        // 리턴값 구성
        result.put("userId", userId);
        result.put("name", name);

        return result;
    }
}
