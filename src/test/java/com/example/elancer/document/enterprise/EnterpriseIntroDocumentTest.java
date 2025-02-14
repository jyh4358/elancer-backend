package com.example.elancer.document.enterprise;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.MainBusinessHelper;
import com.example.elancer.common.SubBusinessHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.dto.EnterpriseProfileRequest;
import com.example.elancer.enterprise.repository.MainBusinessRepository;
import com.example.elancer.enterprise.repository.SubBusinessRepository;
import com.example.elancer.enterprise.service.EnterpriseService;
import com.example.elancer.integrate.enterprise.EnterpriseLoginHelper;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnterpriseIntroDocumentTest extends DocumentBaseTest {

    @Autowired
    EnterpriseService enterpriseService;

    @Autowired
    MainBusinessRepository mainBusinessRepository;

    @Autowired
    SubBusinessRepository subBusinessRepository;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }

    @Test
    @DisplayName("기업 프로필 수정 문서화")
    public void 기업_프로필_수정_문서화() throws Exception {

        //given
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MainBusinessHelper.사업분야_데이터_생성(mainBusinessRepository);
        SubBusinessHelper.업무분야_데이터_생성(subBusinessRepository);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        List<String> mainBizCodes = new ArrayList<>();
        mainBizCodes.add("main_biz1");
        mainBizCodes.add("main_biz2");
        mainBizCodes.add("main_biz3");
        List<String> subBizCodes = new ArrayList<>();
        subBizCodes.add("sub_biz1");
        subBizCodes.add("sub_biz2");
        subBizCodes.add("sub_etc");

        EnterpriseProfileRequest enterpriseProfileRequest = new EnterpriseProfileRequest(
                "프로필 title",
                "SI",
                100000000L,
                "123-123-123",
                mainBizCodes,
                "",
                subBizCodes,
                "업무 분야 기타",
                "사업자 등록증 url"
        );



        mockMvc.perform(put("/enterprise/profile")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                    .content(objectMapper.writeValueAsString(enterpriseProfileRequest)))
                .andExpectAll(status().isOk())
                .andDo(print())
                .andDo(document("enterprise-profile",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")

                        ),
                        requestFields(
                                fieldWithPath("introTitle").type("String").description("프로필 타이틀 필드"),
                                fieldWithPath("bizContents").type("String").description("주요 사업 내용"),
                                fieldWithPath("sales").type("Long").description("연간 매출액"),
                                fieldWithPath("idNumber").type("String").description("사업자 등록 번호"),
                                fieldWithPath("mainBizCodes").type("List<String>").description("사업 분야"),
                                fieldWithPath("mainEtc").type("String").description("사업 분야 기타"),
                                fieldWithPath("subBizCodes").type("List<String>").description("업무 분야"),
                                fieldWithPath("subEtc").type("String").description("업무 분야 기타"),
                                fieldWithPath("bizRegistration").type("String").description("사업자 등록증 url")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답 ")
                        ),
                        responseFields(
                                fieldWithPath("introTitle").type("String").description("프로필 타이틀 필드"),
                                fieldWithPath("bizContents").type("String").description("주요 사업 내용"),
                                fieldWithPath("sales").type("Long").description("연간 매출액"),
                                fieldWithPath("idNumber").type("String").description("사업자 등록 번호"),
                                fieldWithPath("mainBizCodes").type("List<String>").description("사업 분야"),
                                fieldWithPath("mainEtc").type("String").description("사업 분야 기타"),
                                fieldWithPath("subBizCodes").type("List<String>").description("업무 분야"),
                                fieldWithPath("subEtc").type("String").description("업무 분야 기타"),
                                fieldWithPath("bizRegistration").type("String").description("사업자 등록증 url")
                        )
                ));
    }

    @Test
    @DisplayName("기업 프로필 조회 문서화")
    public void 기업_프로필_조회_문서화() throws Exception {
        //given
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MainBusinessHelper.사업분야_데이터_생성(mainBusinessRepository);
        SubBusinessHelper.업무분야_데이터_생성(subBusinessRepository);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        List<String> mainBizCodes = new ArrayList<>();
        mainBizCodes.add("main_biz1");
        mainBizCodes.add("main_biz2");
        mainBizCodes.add("main_biz3");
        List<String> subBizCodes = new ArrayList<>();
        subBizCodes.add("sub_biz1");
        subBizCodes.add("sub_biz2");
        subBizCodes.add("sub_etc");

        MemberDetails memberDetails = new MemberDetails(
                enterprise.getNum(),
                enterprise.getUserId(),
                enterprise.getRole());


        EnterpriseProfileRequest enterpriseProfileRequest = new EnterpriseProfileRequest(
                "프로필 title",
                "SI",
                100000000L,
                "123-123-123",
                mainBizCodes,
                "",
                subBizCodes,
                "업무 분야 기타",
                "사업자 등록증 url"
        );

        enterpriseService.updateIntro(memberDetails, enterpriseProfileRequest);

        mockMvc.perform(get("/enterprise/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("enterprise-profile-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답 ")
                        ),
                        responseFields(
                                fieldWithPath("introTitle").type("String").description("프로필 타이틀 필드"),
                                fieldWithPath("bizContents").type("String").description("주요 사업 내용"),
                                fieldWithPath("sales").type("Long").description("연간 매출액"),
                                fieldWithPath("idNumber").type("String").description("사업자 등록 번호"),
                                fieldWithPath("mainBizCodes").type("List<String>").description("사업 분야"),
                                fieldWithPath("mainEtc").type("String").description("사업 분야 기타"),
                                fieldWithPath("subBizCodes").type("List<String>").description("업무 분야"),
                                fieldWithPath("subEtc").type("String").description("업무 분야 기타"),
                                fieldWithPath("bizRegistration").type("String").description("사업자 등록증 url")
                        )
                ));



    }



}
