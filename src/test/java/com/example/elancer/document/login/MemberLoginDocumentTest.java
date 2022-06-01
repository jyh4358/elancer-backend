package com.example.elancer.document.login;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.integrate.enterprise.EnterpriseLoginHelper;
import com.example.elancer.login.auth.dto.AuthCode;
import com.example.elancer.login.auth.dto.GoogleProfile;
import com.example.elancer.login.auth.property.SocialProperty;
import com.example.elancer.member.dto.MemberLoginRequest;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.member.dto.MemberOAuthLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.token.jwt.property.JwtProperty;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberLoginDocumentTest extends DocumentBaseTest {

    @Autowired
    private SocialProperty socialProperty;

    @AfterEach
    void tearDown() {
        databaseClean.clean();
    }


    @Test
    @DisplayName("기업 로그인 문서화 테스트")
    public void 회원_로그인_문서화() throws Exception {
        EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(
                "enterpriseId",
                "pwd"
        );

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(memberLoginRequest)))
                .andExpectAll(status().isOk())
                .andDo(print())
                .andDo(document("member-login",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                                ),
                        requestFields(
                                fieldWithPath("userId").type("String").description("회원 아이디"),
                                fieldWithPath("password").type("String").description("패스워드")
                        ),
                        responseHeaders(
                                headerWithName(org.springframework.http.HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("memberType").type("MemberType").description("회원 이름"),
                                fieldWithPath("accessToken").type("String").description("jwt access 토큰"),
                                fieldWithPath("refreshToken").type("String").description("jwt refresh 토큰")
                        )
                ));
    }

    @Test
    @DisplayName("회원 엑세스 토큰 만료시 재발급 문서화")
    public void access_token_만료시_재발급_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);


        mockMvc.perform(get("/reissue")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .header(JwtTokenProvider.REFRESH_KEY, memberLoginResponse.getRefreshToken()))
                .andExpectAll(status().isOk())
                .andDo(print())
                .andDo(document("reissue",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드."),
                                headerWithName(JwtTokenProvider.REFRESH_KEY).description("jwt refresh 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(org.springframework.http.HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type("String").description("jwt access 토큰"),
                                fieldWithPath("refreshToken").type("String").description("jwt refresh 토큰")
                        )
                ));
    }




    // todo - 이부분은 다시 찾아봐야 할듯.. 해당 URL로 요청하게 되면 form으로 구글 id, password를 받는데 이부분을 어떻게 테스트 코드 구현할지..
    @Test
    @DisplayName("Authentication Code 요청 문서화")
    public void 권한_코드_요청_문서화() throws Exception {
        StringBuilder requestUrl = new StringBuilder()
                .append(socialProperty.getUrlLogin())
                .append("?client_id=").append(socialProperty.getClientId())
                .append("&response_type=code")
                .append("&scope=email%20profile")
                .append("&redirect_uri=").append(socialProperty.getRedirect());

        mockMvc.perform(post(String.valueOf(requestUrl)))
                .andDo(print())
                .andDo(document("authentication-code-request"));
    }

    // todo - 이부분은 다시 찾아봐야 할듯.. 내부에서 restTemplate가 작동하고, 유효한 Authentication code 가 필요한데 여긴 어떻게 테스트 작성할지 생각해보자.

    @Test
    @DisplayName("회원 가입이 된 소셜 로그인")
    public void 소셜_로그인() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);

        AuthCode authCode = new AuthCode("Authentication Code");

        GoogleProfile googleProfile = new GoogleProfile(
                "123123123",
                "홍길동",
                "test@gmail.com",
                "pictureUrl"
        );

        MemberOAuthLoginResponse memberOAuthLoginResponse = new MemberOAuthLoginResponse(
                "홍길동",
                "test@gmail.com",
                "accessToken",
                "refreshToken"
        );

        mockMvc.perform(post("/login/google")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authCode)))
                .andDo(print())
                .andDo(document("social-login",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        requestFields(
                                fieldWithPath("code").type("String").description("Authentication Code")
                        )
                ))
                .andReturn();

    }




}
