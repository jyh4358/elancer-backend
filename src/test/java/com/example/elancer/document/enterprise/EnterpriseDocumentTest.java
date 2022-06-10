package com.example.elancer.document.enterprise;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
import com.example.elancer.enterprise.dto.EnterpriseUpdateRequest;
import com.example.elancer.integrate.enterprise.EnterpriseLoginHelper;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnterpriseDocumentTest extends DocumentBaseTest {


    @AfterEach
    void tearDown() {
        databaseClean.clean();
    }

    @Test
    @DisplayName("기업 회원가입 문서화 테스트")
    public void 기업_회원가입_문서화() throws Exception {
        EnterpriseJoinRequest enterpriseJoinRequest = new EnterpriseJoinRequest(
                "joinDocsEnterprise",
                "1234",
                "1234",
                "name",
                "01000000000",
                "test@gmail.com",
                "test company",
                10,
                "사장",
                "01011111111",
                "www.test.com",
                new Address(CountryType.KR, "123", "주소1", "주소2"),
                "주요 사업",
                10000000L,
                "사업자 번호(123-123-123)"
        );

        mockMvc.perform(post("/enterprise")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(enterpriseJoinRequest)))
                .andExpectAll(status().isCreated())
                .andDo(print())
                .andDo(document("enterprise-join",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        requestFields(
                                fieldWithPath("userId").type("String").description("회원 아이디 필드"),
                                fieldWithPath("password1").type("String").description("회원 비밀번호 필드"),
                                fieldWithPath("password2").type("String").description("회원 비밀번호 확인 필드"),
                                fieldWithPath("name").type("String").description("회원 담당자 성명 필드"),
                                fieldWithPath("phone").type("String").description("회원 회사 전화번호 필드"),
                                fieldWithPath("email").type("String").description("회원 이메일 필드"),
                                fieldWithPath("companyName").type("String").description("회원 회사명 필드"),
                                fieldWithPath("companyPeople").type("Integer").description("회원 회사 인원수 필드"),
                                fieldWithPath("position").type("String").description("회원 담당자 직책 필드"),
                                fieldWithPath("telNumber").type("String").description("회원 담당자 휴대폰 필드"),
                                fieldWithPath("website").type("String").description("회원 웹사이트 필드"),
                                fieldWithPath("address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("bizContents").type("String").description("회원 주요 사업내용 필드"),
                                fieldWithPath("sales").type("Long").description("회원 연간 매출액 필드"),
                                fieldWithPath("idNumber").type("String").description("회원 사업자 번호 필드")
                        )
                ));
    }

    @DisplayName("기업 계정 정보 수정 문서화")
    @Test
    public void 기업_계정정보_수정_문서화() throws Exception{
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        EnterpriseUpdateRequest enterpriseUpdateRequest = new EnterpriseUpdateRequest(
                "변경된 회사 이름",
                20,
                "변경된 이름",
                "부장",
                "12345",
                "12345",
                "01033333333",
                "01044444444",
                "changedEmail@gmail.com",
                "www.changedWebsite.com",
                new Address(CountryType.CN, "경기도", "주소1", "주소2"),
                "쇼핑몰",
                200000000L,
                "111-111-111"
        );

        mockMvc.perform(put("/enterprise")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(enterpriseUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("enterprise-account-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("companyName").type("String").description("회사명"),
                                fieldWithPath("companyPeople").type("Integer").description("회사 인원수"),
                                fieldWithPath("name").type("String").description("담당자명"),
                                fieldWithPath("position").type("String").description("직책"),
                                fieldWithPath("password1").type("String").description("비밀번호"),
                                fieldWithPath("password2").type("String").description("비밀번호 확인"),
                                fieldWithPath("phone").type("String").description("전화번호"),
                                fieldWithPath("telNumber").type("String").description("담당자 휴대폰"),
                                fieldWithPath("email").type("String").description("이메일"),
                                fieldWithPath("website").type("String").description("웹사이트"),
                                fieldWithPath("address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("bizContents").type("String").description("주요 사업내용"),
                                fieldWithPath("sales").type("Long").description("연간 매출액"),
                                fieldWithPath("idNumber").type("String").description("사업자등록번호")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("companyName").type("String").description("회사명"),
                                fieldWithPath("companyPeople").type("Integer").description("회사 인원수"),
                                fieldWithPath("name").type("String").description("담당자명"),
                                fieldWithPath("position").type("String").description("직책"),
                                fieldWithPath("phone").type("String").description("전화번호"),
                                fieldWithPath("telNumber").type("String").description("담당자 휴대폰"),
                                fieldWithPath("email").type("String").description("이메일"),
                                fieldWithPath("website").type("String").description("웹사이트"),
                                fieldWithPath("address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("bizContents").type("String").description("주요 사업내용"),
                                fieldWithPath("sales").type("Long").description("연간 매출액"),
                                fieldWithPath("idNumber").type("String").description("사업자등록번호")
                        )
                ));
    }

    @DisplayName("기업 계정 정보 조회 문서화")
    @Test
    public void 기업_계정정보_조회_문서화() throws Exception{

        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        mockMvc.perform(get("/enterprise")
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("enterprise-account-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("companyName").type("String").description("회사명"),
                                fieldWithPath("companyPeople").type("Integer").description("회사 인원수"),
                                fieldWithPath("name").type("String").description("담당자명"),
                                fieldWithPath("position").type("String").description("직책"),
                                fieldWithPath("phone").type("String").description("전화번호"),
                                fieldWithPath("telNumber").type("String").description("담당자 휴대폰"),
                                fieldWithPath("email").type("String").description("이메일"),
                                fieldWithPath("website").type("String").description("웹사이트"),
                                fieldWithPath("address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("bizContents").type("String").description("주요 사업내용"),
                                fieldWithPath("sales").type("Long").description("연간 매출액"),
                                fieldWithPath("idNumber").type("String").description("사업자등록번호")
                        )
                ));

    }

    @DisplayName("기업 대쉬보드 프로필 조회 문서화")
    @Test
    public void 기업_대쉬보드_프로필_조회_문서화() throws Exception{

        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        mockMvc.perform(get("/enterprise-profile")
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("dashboard-profile",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("expertise").type("int").description("전문성"),
                                fieldWithPath("scheduleAdherence").type("int").description("일정준수"),
                                fieldWithPath("initiative").type("int").description("적극성"),
                                fieldWithPath("communication").type("int").description("의사소통"),
                                fieldWithPath("reEmploymentIntention").type("int").description("재고용 의사"),
                                fieldWithPath("totalActiveScore").type("double").description("활동 평가"),
                                fieldWithPath("enterpriseType").type("String").description("기업형태"),
                                fieldWithPath("bizContents").type("String").description("사업자등록번호"),
                                fieldWithPath("sales").type("Long").description("연간 매출액")
                        )
                ));

    }
}
