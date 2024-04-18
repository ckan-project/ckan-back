package com.hanyang.dataportal.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyang.dataportal.core.component.ApiResponseBuilder;
import com.hanyang.dataportal.core.component.CustomAuthenticationEntryPoint;
import com.hanyang.dataportal.core.config.SecurityConfig;
import com.hanyang.dataportal.core.filter.JwtAuthenticationFilter;
import com.hanyang.dataportal.core.jwt.component.AuthorizationExtractor;
import com.hanyang.dataportal.core.jwt.component.JwtTokenProvider;
import com.hanyang.dataportal.core.jwt.component.JwtTokenResolver;
import com.hanyang.dataportal.core.jwt.component.JwtTokenValidator;
import com.hanyang.dataportal.core.jwt.dto.TokenDto;
import com.hanyang.dataportal.core.response.ApiResponse;
import com.hanyang.dataportal.user.domain.Provider;
import com.hanyang.dataportal.user.dto.req.ReqOauthDto;
import com.hanyang.dataportal.user.dto.res.ResLoginDto;
import com.hanyang.dataportal.user.service.OauthLoginService;
import com.hanyang.dataportal.user.service.UserLoginService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@WebMvcTest(UserLoginController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class UserLoginControllerTest {
    private final String CODE = "code";
    private final String ACCESS_TOKEN = "access_token";
    private final String REFRESH_TOKEN = "refresh_token";

    @Autowired
    UserLoginController userLoginController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserLoginService userLoginService;
    @MockBean
    OauthLoginService oauthLoginService;

    // filter 관련 Bean 설정
    @MockBean
    ApiResponseBuilder apiResponseBuilder;
    @MockBean
    JwtTokenResolver jwtTokenResolver;
    @MockBean
    JwtTokenValidator jwtTokenValidator;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @MockBean
    CorsConfigurationSource corsConfigurationSource;

    @DisplayName("소셜 로그인 성공 테스트")
    @Test
    void 카카오_로그인_정상_케이스() throws Exception {
        // given
        final ReqOauthDto reqOauthDto = new ReqOauthDto(CODE);
        final TokenDto tokenDto = TokenDto.builder()
                .accessToken(ACCESS_TOKEN)
                .refreshToken(REFRESH_TOKEN)
                .build();

        when(oauthLoginService.login(Provider.KAKAO.value(), CODE))
                .thenReturn(tokenDto);

        final ResponseCookie responseCookie = ResponseCookie
                .from(JwtTokenProvider.REFRESH_COOKIE_KEY, REFRESH_TOKEN)
                .build();
        when(userLoginService.generateRefreshCookie(any()))
                .thenReturn(responseCookie);

        // when
        final MvcResult mvcResult = mockMvc.perform(post("/api/user/login/{provider}", Provider.KAKAO.value())
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(reqOauthDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

//        final String refresh_token = mvcResult.getResponse()
//                .getCookie(JwtTokenProvider.REFRESH_COOKIE_KEY)
//                .getValue();
//
//        final ResLoginDto result = (ResLoginDto) objectMapper.readValue(
//                mvcResult.getResponse().getContentAsString(),
//                ApiResponse.class).getResult();
//        final String access_token = result.getAccessToken();
//
//        // then
//        Assertions.assertThat(access_token).isEqualTo(ACCESS_TOKEN);
//        Assertions.assertThat(refresh_token).isEqualTo(REFRESH_TOKEN);
    }
}