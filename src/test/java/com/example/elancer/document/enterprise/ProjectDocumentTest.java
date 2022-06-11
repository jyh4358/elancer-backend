package com.example.elancer.document.enterprise;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.integrate.enterprise.EnterpriseLoginHelper;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.dto.ProjectDeleteRequest;
import com.example.elancer.project.dto.ProjectSaveRequest;
import com.example.elancer.project.model.*;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectDocumentTest extends DocumentBaseTest {

    @Autowired
    private ProjectRepository projectRepository;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }


    @Test
    @DisplayName("프로젝트 등록 Get 요청 문서화 테스트")
    public void 프로젝트_등록_GET_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        mockMvc.perform(get("/project-save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project-save-find",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("companyName").type("String").description("회사명"),
                                fieldWithPath("name").type("String").description("담당자명"),
                                fieldWithPath("position").type("String").description("직책"),
                                fieldWithPath("telNumber").type("String").description("회사 전화번호"),
                                fieldWithPath("phone").type("String").description("담당자 전화번호"),
                                fieldWithPath("email").type("String").description("이메일")
                        )

                ));
    }

    @Test
    @DisplayName("프로젝트 등록 문서화 테스트")
    public void 프로젝트_등록_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        ProjectSaveRequest projectSaveRequest = new ProjectSaveRequest(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                EnterpriseLogo.COUPANG,
                ProjectStep.ANALYSIS,
                "쇼핑몰",
                PositionKind.DEVELOPER,
                "자바",
                "쇼핑몰 프로젝트",
                10,
                10,
                "프로젝트 상세내용",
                LocalDate.now(),
                LocalDate.now().plusMonths(1L),
                LocalDate.now().plusDays(5L),
                new Address(CountryType.KR, "우편번호", "주소", "상세주소"),
                1000000,
                10000000,
                3,
                3,
                30,
                35,
                "테스트회사",
                "담당자명",
                "사장",
                "010-0000-0000",
                "010-1111-1111",
                "project@gmail.com");

        mockMvc.perform(post("/project-save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(projectSaveRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project-save",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("projectType").type("ProjectType").description("TELEWORKING(재택), WORKING(상주)"),
                                fieldWithPath("projectBackGround").type("ProjectBackGround").description("색상"),
                                fieldWithPath("enterpriseLogo").type("EnterpriseLogo").description("SAMSUNG(삼성), LG(LG), KT(KT), SK(SK), COUPANG(쿠팡), WOOWAHAN(우아한형제들),LOTTE(롯데), NONGYUP(농협), SHINHAN(신한), IBK(기업), KBSTAR(국민)"),
                                fieldWithPath("projectStep").type("ProjectStep").description("ANALYSIS(분석/설계), PLAN(기획), DESIGN(디자인), PUBLISHING(퍼블리싱), DEVELOP(개발), OPERATION(운영중)"),
                                fieldWithPath("mainBiz").type("String").description("업무 분야"),
                                fieldWithPath("positionKind").type("PositionKind").description("DEVELOPER(개발자), PUBLISHER(퍼블리셔), DESIGNER(디자이너), PLANNER(기획자), CROWD_WORKER(크라우드워커), ETC(기타)"),
                                fieldWithPath("skill").type("String").description("관련 기술"),
                                fieldWithPath("projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("headCount").type("Integer").description("모집 인원"),
                                fieldWithPath("inputHeadCount").type("Integer").description("총 투입인력"),
                                fieldWithPath("content").type("String").description("프로젝트 상세내용"),
                                fieldWithPath("projectStateDate").type("LocalDate").description("프로젝트 시작 날짜"),
                                fieldWithPath("projectEndDate").type("LocalDate").description("프로젝트 종료 날짜"),
                                fieldWithPath("recruitEndDate").type("LocalDate").description("프로젝트 모집 마감일"),
                                fieldWithPath("address.country").type("CountryType.STRING").description("근무지 주소 국적 필드"),
                                fieldWithPath("address.zipcode").type("String").description("근무지 우편번호 필드"),
                                fieldWithPath("address.mainAddress").type("String").description("근무지 주소 필드"),
                                fieldWithPath("address.detailAddress").type("String").description("근무지 상세 주소 필드"),
                                fieldWithPath("minMoney").type("Integer").description("예상 월 단가 최소"),
                                fieldWithPath("maxMoney").type("Integer").description("예상 월 단가 최대"),
                                fieldWithPath("careerYear").type("Integer").description("희망 경력(년)"),
                                fieldWithPath("careerMonth").type("Integer").description("희망 경력(월)"),
                                fieldWithPath("minDesiredAge").type("Integer").description("희망 연령 최소"),
                                fieldWithPath("maxDesiredAge").type("Integer").description("희망 연령 최대"),
                                fieldWithPath("companyName").type("String").description("회사명"),
                                fieldWithPath("name").type("String").description("담당자명"),
                                fieldWithPath("position").type("String").description("직책"),
                                fieldWithPath("phone").type("String").description("회사 전화번호"),
                                fieldWithPath("telNumber").type("String").description("담당자 휴대폰"),
                                fieldWithPath("email").type("String").description("이메일")
                        )
                ));
    }
    @Test
    @DisplayName("프로젝트 삭제 문서화 테스트")
    public void 프로젝트_삭제_문서화() throws Exception{
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        Project project = projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                EnterpriseLogo.COUPANG,
                ProjectStep.ANALYSIS,
                "쇼핑몰",
                PositionKind.DEVELOPER,
                "Java",
                "쇼핑몰 프로젝트",
                5,
                5,
                "1.프로젝트 명 .....",
                LocalDate.now(),
                LocalDate.now().plusMonths(1L),
                LocalDate.now().plusDays(10L),
                new Address(CountryType.KR, "123-123", "메인 주소", "상세 주소"),
                6000000,
                10000000,
                5,
                3,
                30,
                35,
                ProjectStatus.PROGRESS
        ));

        ProjectDeleteRequest projectDeleteRequest = new ProjectDeleteRequest(
                project.getNum()
        );

        mockMvc.perform(delete("/project-delete")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(projectDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        requestFields(
                                fieldWithPath("projectNum").type("Long").description("프로젝트 식별자")
                        )

                ));
    }
}
