package com.example.elancer.document.project;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.integrate.enterprise.EnterpriseLoginHelper;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.dto.ProjectDeleteRequest;
import com.example.elancer.project.dto.ProjectProcessingRequest;
import com.example.elancer.project.dto.ProjectSaveRequest;
import com.example.elancer.project.dto.ProjectSearchCondition;
import com.example.elancer.project.model.*;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.project.service.ProjectService;
import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.waitproject.repsitory.WaitProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectDocumentTest extends DocumentBaseTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ApplyProjectRepository applyProjectRepository;

    @Autowired
    private InterviewProjectRepository interviewProjectRepository;

    @Autowired
    private WaitProjectRepository waitProjectRepository;

    @Autowired
    private ProjectService projectService;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }


    @Test
    @DisplayName("프로젝트 세부정보 요청 문서화 테스트")
    public void 프로젝트_세부정보_GET_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));

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
                ProjectStatus.PROGRESS,
                enterprise
        ));

        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));

        mockMvc.perform(get("/project/{projectNum}", project.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project-detail",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("projectName").type("String").description("프로젝트명"),
                                fieldWithPath("pay").type("String").description("프로젝트 단가"),
                                fieldWithPath("freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\"), MIDDLE(\"중급\"), SENIOR(\"고급\")"),
                                fieldWithPath("projectPeriod").type("Long").description("프로젝트 기간(Month)"),
                                fieldWithPath("address.country").type("CountryType.STRING").description("근무지 주소 국적 필드"),
                                fieldWithPath("address.zipcode").type("String").description("근무지 우편번호 필드"),
                                fieldWithPath("address.mainAddress").type("String").description("근무지 주소 필드"),
                                fieldWithPath("address.detailAddress").type("String").description("근무지 상세 주소 필드"),
                                fieldWithPath("skills").type("List<String>").description("스킬 정보"),
                                fieldWithPath("headCount").type("Integer").description("모집 인원"),
                                fieldWithPath("inputHeadCount").type("Integer").description("총 투입인력"),
                                fieldWithPath("content").type("String").description("프로젝트 내용"),
                                fieldWithPath("endDays").type("Long").description("프로젝트 지원 마감일자(day)"),
                                fieldWithPath("projectStep").type("ProjectStep").description("\"ANALYSIS(분석/설계), PLAN(기획), DESIGN(디자인), PUBLISHING(퍼블리싱), DEVELOP(개발), OPERATION(운영중)\""),
                                fieldWithPath("simpleFreelancerList.[].thumbnailUrl").type("String").description("지원 프리랜서 프로필 URL"),
                                fieldWithPath("simpleFreelancerList.[].username").type("String").description("지원 프리랜서 이름")
                        )

                ));
    }

    @Test
    @DisplayName("프로젝트 포지션 타입에 따른 리스트 문서화 테스트")
    public void 프로젝트_포지션_타입에_따른_리스트_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));

        projectRepository.save(new Project(
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
                ProjectStatus.PROGRESS,
                enterprise
        ));

        ProjectSearchCondition projectSearchCondition = new ProjectSearchCondition();



        mockMvc.perform(get("/project-list?position=DEVELOPER&skill=Java")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(projectSearchCondition)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project-list",
                        requestParameters(
                                parameterWithName("position").description("DEVELOPER, PUBLISHER, DESIGNER, PLANNER, ETC"),
                                parameterWithName("skill").description("스킬")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("projectBoxResponses.[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("projectBoxResponses.[].projectType").type("ProjectType").description("TELEWORKING(\"재택\"), WORKING(\"상주\")"),
                                fieldWithPath("projectBoxResponses.[].projectBackGround").type("ProjectBackGround").description("BLACK, WHITE, BLUE, INDIGO, ROSSYBROWN, BROWN, CHOCOLATE, ORANGE"),
                                fieldWithPath("projectBoxResponses.[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("projectBoxResponses.[].endDays").type("Long").description("프로젝트 지원 마감일자(day)"),
                                fieldWithPath("projectBoxResponses.[].skills").type("List<String>").description("스킬 정보"),
                                fieldWithPath("projectBoxResponses.[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("projectBoxResponses.[].freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\"), MIDDLE(\"중급\"), SENIOR(\"고급\")"),
                                fieldWithPath("projectBoxResponses.[].projectPeriod").type("Long").description("프로젝트 기간(Month)"),
                                fieldWithPath("projectBoxResponses.[].address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("projectBoxResponses.[].address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("projectBoxResponses.[].address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("projectBoxResponses.[].address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("projectBoxResponses.[].content").type("String").description("프로젝트 내용"),
                                fieldWithPath("projectBoxResponses.[].pay").type("String").description("급여 정보(비공개, 협의가능, 급여)"),
                                fieldWithPath("hasNext").type("boolean").description("다음 페이지 여부")

                        )

                ));
    }

    @Test
    @DisplayName("프로젝트 검색 필터에 따른 리스트 문서화 테스트")
    public void 프로젝트_검색_필터에_따른_리스트_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));

        projectRepository.save(new Project(
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
                ProjectStatus.PROGRESS,
                enterprise
        ));

        ProjectSearchCondition projectSearchCondition = new ProjectSearchCondition(
                PositionKind.DEVELOPER,
                Arrays.asList("java", "spring"),
                ProjectType.BOTH_TELEWORKING_WORKING,
                FreelancerWorkmanShip.MIDDLE,
                "서울",
                ""
        );


        mockMvc.perform(get("/project-list")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(projectSearchCondition)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project-list-search",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("skills").type("List<String>").description("스킬"),
                                fieldWithPath("projectType").type("ProjectType").description("TELEWORKING(\"재택\"), WORKING(\"상주\"), BOTH_TELEWORKING_WORKING(\"재택,상주\")"),
                                fieldWithPath("freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\", 5), MIDDLE(\"중급\", 10), SENIOR(\"고급\", 15)"),
                                fieldWithPath("region").type("String").description("지역1"),
                                fieldWithPath("searchKey").type("String").description("검색 단어")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("projectBoxResponses.[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("projectBoxResponses.[].projectType").type("ProjectType").description("TELEWORKING(\"재택\"), WORKING(\"상주\")"),
                                fieldWithPath("projectBoxResponses.[].projectBackGround").type("ProjectBackGround").description("BLACK, WHITE, BLUE, INDIGO, ROSSYBROWN, BROWN, CHOCOLATE, ORANGE"),
                                fieldWithPath("projectBoxResponses.[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("projectBoxResponses.[].endDays").type("Long").description("프로젝트 지원 마감일자(day)"),
                                fieldWithPath("projectBoxResponses.[].skills").type("List<String>").description("스킬 정보"),
                                fieldWithPath("projectBoxResponses.[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("projectBoxResponses.[].freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\"), MIDDLE(\"중급\"), SENIOR(\"고급\")"),
                                fieldWithPath("projectBoxResponses.[].projectPeriod").type("Long").description("프로젝트 기간(Month)"),
                                fieldWithPath("projectBoxResponses.[].address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("projectBoxResponses.[].address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("projectBoxResponses.[].address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("projectBoxResponses.[].address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("projectBoxResponses.[].content").type("String").description("프로젝트 내용"),
                                fieldWithPath("projectBoxResponses.[].pay").type("String").description("급여 정보(비공개, 협의가능, 급여)"),
                                fieldWithPath("hasNext").type("boolean").description("다음 페이지 여부")

                        )

                ));
    }

    @Test
    @DisplayName("프로젝트 메인 페이지 리스트 요청 문서화 테스트")
    public void 프로젝트_메인_페이지_리스트_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));

        Project project = projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                EnterpriseLogo.COUPANG,
                ProjectStep.DEVELOP,
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
                ProjectStatus.PROGRESS,
                enterprise
        ));
        projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                EnterpriseLogo.COUPANG,
                ProjectStep.DEVELOP,
                "쇼핑몰",
                PositionKind.PUBLISHER,
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
                ProjectStatus.PROGRESS,
                enterprise
        ));
        projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                EnterpriseLogo.COUPANG,
                ProjectStep.DEVELOP,
                "쇼핑몰",
                PositionKind.DESIGNER,
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
                ProjectStatus.PROGRESS,
                enterprise
        ));
        projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                EnterpriseLogo.COUPANG,
                ProjectStep.DEVELOP,
                "쇼핑몰",
                PositionKind.PLANNER,
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
                ProjectStatus.PROGRESS,
                enterprise
        ));
        projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                EnterpriseLogo.COUPANG,
                ProjectStep.DEVELOP,
                "쇼핑몰",
                PositionKind.ETC,
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
                ProjectStatus.PROGRESS,
                enterprise
        ));



        mockMvc.perform(get("/project-index-list", project.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project-index-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("developerProjectList.[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("developerProjectList.[].projectType").type("ProjectType").description("TELEWORKING(\"재택\"), WORKING(\"상주\")"),
                                fieldWithPath("developerProjectList.[].projectBackGround").type("ProjectBackGround").description("BLACK, WHITE, BLUE, INDIGO, ROSSYBROWN, BROWN, CHOCOLATE, ORANGE"),
                                fieldWithPath("developerProjectList.[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("developerProjectList.[].endDays").type("Long").description("프로젝트 지원 마감일자(day)"),
                                fieldWithPath("developerProjectList.[].skills").type("List<String>").description("스킬 정보"),
                                fieldWithPath("developerProjectList.[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("developerProjectList.[].freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\"), MIDDLE(\"중급\"), SENIOR(\"고급\")"),
                                fieldWithPath("developerProjectList.[].projectPeriod").type("Long").description("프로젝트 기간(Month)"),
                                fieldWithPath("developerProjectList.[].address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("developerProjectList.[].address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("developerProjectList.[].address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("developerProjectList.[].address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("developerProjectList.[].content").type("String").description("프로젝트 내용"),
                                fieldWithPath("developerProjectList.[].pay").type("String").description("급여 정보(비공개, 협의가능, 급여)"),
                                fieldWithPath("publisherProjectList.[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("publisherProjectList.[].projectType").type("ProjectType").description("TELEWORKING(\"재택\"), WORKING(\"상주\")"),
                                fieldWithPath("publisherProjectList.[].projectBackGround").type("ProjectBackGround").description("BLACK, WHITE, BLUE, INDIGO, ROSSYBROWN, BROWN, CHOCOLATE, ORANGE"),
                                fieldWithPath("publisherProjectList.[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("publisherProjectList.[].endDays").type("Long").description("프로젝트 지원 마감일자(day)"),
                                fieldWithPath("publisherProjectList.[].skills").type("List<String>").description("스킬 정보"),
                                fieldWithPath("publisherProjectList.[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("publisherProjectList.[].freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\"), MIDDLE(\"중급\"), SENIOR(\"고급\")"),
                                fieldWithPath("publisherProjectList.[].projectPeriod").type("Long").description("프로젝트 기간(Month)"),
                                fieldWithPath("publisherProjectList.[].address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("publisherProjectList.[].address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("publisherProjectList.[].address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("publisherProjectList.[].address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("publisherProjectList.[].content").type("String").description("프로젝트 내용"),
                                fieldWithPath("publisherProjectList.[].pay").type("String").description("급여 정보(비공개, 협의가능, 급여)"),
                                fieldWithPath("designerProjectList.[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("designerProjectList.[].projectType").type("ProjectType").description("TELEWORKING(\"재택\"), WORKING(\"상주\")"),
                                fieldWithPath("designerProjectList.[].projectBackGround").type("ProjectBackGround").description("BLACK, WHITE, BLUE, INDIGO, ROSSYBROWN, BROWN, CHOCOLATE, ORANGE"),
                                fieldWithPath("designerProjectList.[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("designerProjectList.[].endDays").type("Long").description("프로젝트 지원 마감일자(day)"),
                                fieldWithPath("designerProjectList.[].skills").type("List<String>").description("스킬 정보"),
                                fieldWithPath("designerProjectList.[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("designerProjectList.[].freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\"), MIDDLE(\"중급\"), SENIOR(\"고급\")"),
                                fieldWithPath("designerProjectList.[].projectPeriod").type("Long").description("프로젝트 기간(Month)"),
                                fieldWithPath("designerProjectList.[].address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("designerProjectList.[].address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("designerProjectList.[].address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("designerProjectList.[].address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("designerProjectList.[].content").type("String").description("프로젝트 내용"),
                                fieldWithPath("designerProjectList.[].pay").type("String").description("급여 정보(비공개, 협의가능, 급여)"),
                                fieldWithPath("plannerProjectList.[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("plannerProjectList.[].projectType").type("ProjectType").description("TELEWORKING(\"재택\"), WORKING(\"상주\")"),
                                fieldWithPath("plannerProjectList.[].projectBackGround").type("ProjectBackGround").description("BLACK, WHITE, BLUE, INDIGO, ROSSYBROWN, BROWN, CHOCOLATE, ORANGE"),
                                fieldWithPath("plannerProjectList.[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("plannerProjectList.[].endDays").type("Long").description("프로젝트 지원 마감일자(day)"),
                                fieldWithPath("plannerProjectList.[].skills").type("List<String>").description("스킬 정보"),
                                fieldWithPath("plannerProjectList.[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("plannerProjectList.[].freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\"), MIDDLE(\"중급\"), SENIOR(\"고급\")"),
                                fieldWithPath("plannerProjectList.[].projectPeriod").type("Long").description("프로젝트 기간(Month)"),
                                fieldWithPath("plannerProjectList.[].address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("plannerProjectList.[].address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("plannerProjectList.[].address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("plannerProjectList.[].address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("plannerProjectList.[].content").type("String").description("프로젝트 내용"),
                                fieldWithPath("plannerProjectList.[].pay").type("String").description("급여 정보(비공개, 협의가능, 급여)"),
                                fieldWithPath("etcProjectList.[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("etcProjectList.[].projectType").type("ProjectType").description("TELEWORKING(\"재택\"), WORKING(\"상주\")"),
                                fieldWithPath("etcProjectList.[].projectBackGround").type("ProjectBackGround").description("BLACK, WHITE, BLUE, INDIGO, ROSSYBROWN, BROWN, CHOCOLATE, ORANGE"),
                                fieldWithPath("etcProjectList.[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("etcProjectList.[].endDays").type("Long").description("프로젝트 지원 마감일자(day)"),
                                fieldWithPath("etcProjectList.[].skills").type("List<String>").description("스킬 정보"),
                                fieldWithPath("etcProjectList.[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("etcProjectList.[].freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\"), MIDDLE(\"중급\"), SENIOR(\"고급\")"),
                                fieldWithPath("etcProjectList.[].projectPeriod").type("Long").description("프로젝트 기간(Month)"),
                                fieldWithPath("etcProjectList.[].address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("etcProjectList.[].address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("etcProjectList.[].address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("etcProjectList.[].address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("etcProjectList.[].content").type("String").description("프로젝트 내용"),
                                fieldWithPath("etcProjectList.[].pay").type("String").description("급여 정보(비공개, 협의가능, 급여)")
                        )

                ));
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
                ProjectStatus.PROGRESS,
                enterprise
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

    @Test
    @DisplayName("추천 프로젝트 요청 문서화 테스트")
    public void 추천_프로젝트_GET_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);

        for (int i = 0; i < 10; i++) {
            projectRepository.save(new Project(
                    ProjectType.TELEWORKING,
                    ProjectBackGround.BLACK,
                    EnterpriseLogo.COUPANG,
                    ProjectStep.ANALYSIS,
                    "쇼핑몰",
                    PositionKind.DEVELOPER,
                    "Java",
                    "쇼핑몰 프로젝트" + i,
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
                    ProjectStatus.PROGRESS,
                    enterprise
            ));

        }

        mockMvc.perform(get("/recommend-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project-recommend-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("[].projectType").type("ProjectType").description("TELEWORKING(\"재택\"), WORKING(\"상주\")"),
                                fieldWithPath("[].projectBackGround").type("ProjectBackGround").description("BLACK, WHITE, BLUE, INDIGO, ROSSYBROWN, BROWN, CHOCOLATE, ORANGE"),
                                fieldWithPath("[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].endDays").type("Long").description("프로젝트 지원 마감일자(day)"),
                                fieldWithPath("[].skills").type("List<String>").description("스킬 정보"),
                                fieldWithPath("[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("[].freelancerWorkmanShip").type("FreelancerWorkmanShip").description("JUNIOR(\"초급\"), MIDDLE(\"중급\"), SENIOR(\"고급\")"),
                                fieldWithPath("[].projectPeriod").type("Long").description("프로젝트 기간(Month)"),
                                fieldWithPath("[].address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
                                fieldWithPath("[].address.zipcode").type("String").description("회원 우편번호 필드"),
                                fieldWithPath("[].address.mainAddress").type("String").description("회원 주소 필드"),
                                fieldWithPath("[].address.detailAddress").type("String").description("회원 상세 주소 필드"),
                                fieldWithPath("[].content").type("String").description("프로젝트 내용"),
                                fieldWithPath("[].pay").type("String").description("급여 정보(비공개, 협의가능, 급여)")
                        )

                ));
    }

    @Test
    @DisplayName("기업 프로젝트 갯수 요청 문서화 테스트")
    public void 기업_프로젝트_갯수_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        Freelancer freelancer2 = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberDetails memberDetails = new MemberDetails(enterprise.getNum(), enterprise.getUserId(), enterprise.getRole());
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));
        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer2, PositionType.DEVELOPER));
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
                ProjectStatus.PROGRESS,
                enterprise
        ));
        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
        interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));
        interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer2, project));
        waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));
        projectService.startProject(memberDetails, new ProjectProcessingRequest(project.getNum()));
        waitProjectRepository.save(WaitProject.createWaitProject(freelancer2, project));

        mockMvc.perform(get("/project-list-count")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project-list-count",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("applyProjectCount").type("int").description("지원 프로젝트 수"),
                                fieldWithPath("interviewProjectCount").type("int").description("인터뷰 프로젝트 수"),
                                fieldWithPath("waitProjectCount").type("int").description("조율 프로젝트 수"),
                                fieldWithPath("processingProjectCount").type("int").description("진행 프로젝트 수"),
                                fieldWithPath("completionProjectCount").type("int").description("완료 프로젝트 수")
                        )

                ));
    }

    @Test
    @DisplayName("기업 프로젝트 리스트 요청 문서화 테스트")
    public void 기업_프로젝트_리스트_GET_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        Freelancer freelancer2 = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberDetails memberDetails = new MemberDetails(enterprise.getNum(), enterprise.getUserId(), enterprise.getRole());
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));
        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer2, PositionType.DEVELOPER));
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
                ProjectStatus.PROGRESS,
                enterprise
        ));
        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
        interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));
        waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));
        projectService.startProject(memberDetails, new ProjectProcessingRequest(project.getNum()));
        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer2, project));
        interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer2, project));
        waitProjectRepository.save(WaitProject.createWaitProject(freelancer2, project));

        mockMvc.perform(get("/enterprise-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("enterprise-dashboard-project-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("[].projectStatus").type("ProjectStatus").description("REGISTRATION(\"등록\"),  PROGRESS(\"진행\"), COMPLETION(\"완료\")"),
                                fieldWithPath("[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].demandCareer").type("String").description("요구 경력"),
                                fieldWithPath("[].headCount").type("int").description("모집 인원"),
                                fieldWithPath("[].projectStateDate").type("LocalDate").description("프로젝트 시작 날짜"),
                                fieldWithPath("[].projectEndDate").type("LocalDate").description("프로젝트 종료 날짜"),
                                fieldWithPath("[].minMoney").type("int").description("예상 월 단가(최소)"),
                                fieldWithPath("[].maxMoney").type("int").description("예상 월 단가(최대)"),
                                fieldWithPath("[].createdDate").type("LocalDate").description("프로젝트 등록 날짜"),
                                fieldWithPath("[].applyCount").type("int").description("해당 프로젝트에 지원한 프리랜서 수"),
                                fieldWithPath("[].interviewCount").type("int").description("프리랜서에게 인터뷰 요청 개수"),
                                fieldWithPath("[].waitCount").type("int").description("조욜중 프리랜서 수"),
                                fieldWithPath("[].workCount").type("int").description("진행중 프리랜서 수"),
                                fieldWithPath("[].applyFreelancerList.[].num").type("Long").description("지원한 프리랜서 식별자"),
                                fieldWithPath("[].applyFreelancerList.[].name").type("String").description("지원한 프리랜서 이름"),
                                fieldWithPath("[].applyFreelancerList.[].careerYear").type("int").description("지원한 프리랜서 경력"),
                                fieldWithPath("[].applyFreelancerList.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].interviewFreelancerList.[].num").type("String").description("지원한 프리랜서 이름"),
                                fieldWithPath("[].interviewFreelancerList.[].name").type("String").description("지원한 프리랜서 이름"),
                                fieldWithPath("[].interviewFreelancerList.[].phone").type("String").description("지원한 프리랜서 이름"),
                                fieldWithPath("[].interviewFreelancerList.[].interviewStatus").type("InterviewStatus").description("WAITING(\"대기\"), ACCEPT(\"수락\")"),
                                fieldWithPath("[].interviewFreelancerList.[].careerYear").type("int").description("WAITING(\"대기\"), ACCEPT(\"수락\")"),
                                fieldWithPath("[].interviewFreelancerList.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].waitFreelancerList.[].num").type("Long").description("조율중 프리랜서 식별자"),
                                fieldWithPath("[].waitFreelancerList.[].name").type("String").description("조율중 프리랜서 이름"),
                                fieldWithPath("[].waitFreelancerList.[].phone").type("String").description("조율중 프리랜서 핸드폰"),
                                fieldWithPath("[].waitFreelancerList.[].careerYear").type("int").description("조율중 프리랜서 경력"),
                                fieldWithPath("[].waitFreelancerList.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].workFreelancerList").type("List").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].workFreelancerList.[].num").type("Long").description("진행중 프리랜서 식별자"),
                                fieldWithPath("[].workFreelancerList.[].name").type("String").description("진행중 프리랜서 이름"),
                                fieldWithPath("[].workFreelancerList.[].phone").type("String").description("진행중 프리랜서 핸드폰"),
                                fieldWithPath("[].workFreelancerList.[].careerYear").type("int").description("진행중 프리랜서 경력"),
                                fieldWithPath("[].workFreelancerList.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")")
                        )

                ));
    }

    @Test
    @DisplayName("기업 지원 프로젝트 리스트 요청 문서화 테스트")
    public void 기업_지원_프로젝트_리스트_GET_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));
        for (int i = 0; i < 2; i++) {
            Project project = projectRepository.save(new Project(
                    ProjectType.TELEWORKING,
                    ProjectBackGround.BLACK,
                    EnterpriseLogo.COUPANG,
                    ProjectStep.ANALYSIS,
                    "쇼핑몰",
                    PositionKind.DEVELOPER,
                    "Java",
                    "쇼핑몰 프로젝트" + i,
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
                    ProjectStatus.PROGRESS,
                    enterprise
            ));
            applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
            interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));

        }

        mockMvc.perform(get("/apply-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("apply-project-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].demandCareer").type("String").description("요구 경력"),
                                fieldWithPath("[].headCount").type("int").description("모집 인원"),
                                fieldWithPath("[].projectStateDate").type("LocalDate").description("프로젝트 시작 날짜"),
                                fieldWithPath("[].projectEndDate").type("LocalDate").description("프로젝트 종료 날짜"),
                                fieldWithPath("[].minMoney").type("int").description("예상 월 단가(최소)"),
                                fieldWithPath("[].maxMoney").type("int").description("예상 월 단가(최대)"),
                                fieldWithPath("[].createdDate").type("LocalDate").description("프로젝트 등록 날짜"),
                                fieldWithPath("[].applyFreelancerCount").type("int").description("지원 프리랜서 수"),
                                fieldWithPath("[].interviewFreelancerCount").type("int").description("인터뷰 프리랜서 수"),
                                fieldWithPath("[].applyFreelancerList.[].num").type("Long").description("지원 프리랜서 식별자"),
                                fieldWithPath("[].applyFreelancerList.[].name").type("String").description("지원 프리랜서 이름"),
                                fieldWithPath("[].applyFreelancerList.[].careerYear").type("int").description("지원 프리랜서 경력"),
                                fieldWithPath("[].applyFreelancerList.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].interviewFreelancerList.[].num").type("Long").description("인터뷰 프리랜서 식별자"),
                                fieldWithPath("[].interviewFreelancerList.[].name").type("String").description("인터뷰 프리랜서 이름"),
                                fieldWithPath("[].interviewFreelancerList.[].phone").type("String").description("인터뷰 프리랜서 핸드폰"),
                                fieldWithPath("[].interviewFreelancerList.[].interviewStatus").type("InterviewStatus").description("WAITING(\"대기\"), ACCEPT(\"수락\")"),
                                fieldWithPath("[].interviewFreelancerList.[].careerYear").type("int").description("인터뷰 프리랜서 경력"),
                                fieldWithPath("[].interviewFreelancerList.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")")
                        )

                ));
    }
    @Test
    @DisplayName("기업 인터뷰 프로젝트 리스트 요청 문서화 테스트")
    public void 기업_인터뷰_프로젝트_리스트_GET_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));
        for (int i = 0; i < 2; i++) {
            Project project = projectRepository.save(new Project(
                    ProjectType.TELEWORKING,
                    ProjectBackGround.BLACK,
                    EnterpriseLogo.COUPANG,
                    ProjectStep.ANALYSIS,
                    "쇼핑몰",
                    PositionKind.DEVELOPER,
                    "Java",
                    "쇼핑몰 프로젝트" + i,
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
                    ProjectStatus.PROGRESS,
                    enterprise
            ));
            applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
            interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));

        }

        mockMvc.perform(get("/interview-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("interview-project-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].demandCareer").type("String").description("요구 경력"),
                                fieldWithPath("[].headCount").type("int").description("모집 인원"),
                                fieldWithPath("[].projectStateDate").type("LocalDate").description("프로젝트 시작 날짜"),
                                fieldWithPath("[].projectEndDate").type("LocalDate").description("프로젝트 종료 날짜"),
                                fieldWithPath("[].minMoney").type("int").description("예상 월 단가(최소)"),
                                fieldWithPath("[].maxMoney").type("int").description("예상 월 단가(최대)"),
                                fieldWithPath("[].createdDate").type("LocalDate").description("프로젝트 등록 날짜"),
                                fieldWithPath("[].applyFreelancerCount").type("int").description("지원 프리랜서 수"),
                                fieldWithPath("[].interviewFreelancerCount").type("int").description("인터뷰 프리랜서 수"),
                                fieldWithPath("[].applyFreelancerList.[].num").type("Long").description("지원 프리랜서 식별자"),
                                fieldWithPath("[].applyFreelancerList.[].name").type("String").description("지원 프리랜서 이름"),
                                fieldWithPath("[].applyFreelancerList.[].careerYear").type("int").description("지원 프리랜서 경력"),
                                fieldWithPath("[].applyFreelancerList.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].interviewFreelancerList.[].num").type("Long").description("인터뷰 프리랜서 식별자"),
                                fieldWithPath("[].interviewFreelancerList.[].name").type("String").description("인터뷰 프리랜서 이름"),
                                fieldWithPath("[].interviewFreelancerList.[].phone").type("String").description("인터뷰 프리랜서 핸드폰"),
                                fieldWithPath("[].interviewFreelancerList.[].interviewStatus").type("InterviewStatus").description("WAITING(\"대기\"), ACCEPT(\"수락\")"),
                                fieldWithPath("[].interviewFreelancerList.[].careerYear").type("int").description("인터뷰 프리랜서 경력"),
                                fieldWithPath("[].interviewFreelancerList.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")")
                        )

                ));
    }
    @Test
    @DisplayName("기업 대기 프로젝트 리스트 요청 문서화 테스트")
    public void 기업_대기_프로젝트_리스트_GET_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));
        for (int i = 0; i < 2; i++) {
            Project project = projectRepository.save(new Project(
                    ProjectType.TELEWORKING,
                    ProjectBackGround.BLACK,
                    EnterpriseLogo.COUPANG,
                    ProjectStep.ANALYSIS,
                    "쇼핑몰",
                    PositionKind.DEVELOPER,
                    "Java",
                    "쇼핑몰 프로젝트" + i,
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
                    ProjectStatus.PROGRESS,
                    enterprise
            ));
            applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
            interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));
            waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));

        }

        mockMvc.perform(get("/wait-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("wait-project-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].demandCareer").type("String").description("요구 경력"),
                                fieldWithPath("[].headCount").type("int").description("모집 인원"),
                                fieldWithPath("[].projectStateDate").type("LocalDate").description("프로젝트 시작 날짜"),
                                fieldWithPath("[].projectEndDate").type("LocalDate").description("프로젝트 종료 날짜"),
                                fieldWithPath("[].minMoney").type("int").description("예상 월 단가(최소)"),
                                fieldWithPath("[].maxMoney").type("int").description("예상 월 단가(최대)"),
                                fieldWithPath("[].createdDate").type("LocalDate").description("프로젝트 등록 날짜"),
                                fieldWithPath("[].waitFreelancerCount").type("int").description("조욜중 프리랜서 수"),
                                fieldWithPath("[].waitFreelancerList.[].num").type("Long").description("조율중 프리랜서 식별자"),
                                fieldWithPath("[].waitFreelancerList.[].name").type("String").description("조율중 프리랜서 이름"),
                                fieldWithPath("[].waitFreelancerList.[].phone").type("String").description("조율중 프리랜서 핸드폰"),
                                fieldWithPath("[].waitFreelancerList.[].careerYear").type("int").description("조율중 프리랜서 경력"),
                                fieldWithPath("[].waitFreelancerList.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")")
                        )

                ));
    }

    @Test
    @DisplayName("기업 진행 프로젝트 리스트 요청 문서화 테스트")
    public void 기업_진행_프로젝트_리스트_GET_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberDetails memberDetails = new MemberDetails(enterprise.getNum(), enterprise.getUserId(), enterprise.getRole());
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));
        for (int i = 0; i < 2; i++) {
            Project project = projectRepository.save(new Project(
                    ProjectType.TELEWORKING,
                    ProjectBackGround.BLACK,
                    EnterpriseLogo.COUPANG,
                    ProjectStep.ANALYSIS,
                    "쇼핑몰",
                    PositionKind.DEVELOPER,
                    "Java",
                    "쇼핑몰 프로젝트" + i,
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
                    ProjectStatus.PROGRESS,
                    enterprise
            ));
            applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
            interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));
            waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));
            projectService.startProject(memberDetails, new ProjectProcessingRequest(project.getNum()));

        }

        mockMvc.perform(get("/processing-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("processing-project-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].demandCareer").type("String").description("요구 경력"),
                                fieldWithPath("[].headCount").type("int").description("모집 인원"),
                                fieldWithPath("[].projectStateDate").type("LocalDate").description("프로젝트 시작 날짜"),
                                fieldWithPath("[].projectEndDate").type("LocalDate").description("프로젝트 종료 날짜"),
                                fieldWithPath("[].minMoney").type("int").description("예상 월 단가(최소)"),
                                fieldWithPath("[].maxMoney").type("int").description("예상 월 단가(최대)"),
                                fieldWithPath("[].createdDate").type("LocalDate").description("프로젝트 등록 날짜"),
                                fieldWithPath("[].processingFreelancerCount").type("int").description("진행 프로젝트의 프리랜서 수"),
                                fieldWithPath("[].waitFreelancerDto.[].num").type("Long").description("진행 프로젝트의 프리랜서 식별자"),
                                fieldWithPath("[].waitFreelancerDto.[].name").type("String").description("진행 프로젝트의 프리랜서 이름"),
                                fieldWithPath("[].waitFreelancerDto.[].phone").type("String").description("진행 프로젝트의 프리랜서 핸드폰"),
                                fieldWithPath("[].waitFreelancerDto.[].careerYear").type("int").description("진행 프로젝트의 프리랜서 경력"),
                                fieldWithPath("[].waitFreelancerDto.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")")
                        )

                ));
    }

    @Test
    @DisplayName("기업 완료 프로젝트 리스트 요청 문서화 테스트")
    public void 기업_완료_프로젝트_리스트_GET_요청_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberDetails memberDetails = new MemberDetails(enterprise.getNum(), enterprise.getUserId(), enterprise.getRole());
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));
        for (int i = 0; i < 2; i++) {
            Project project = projectRepository.save(new Project(
                    ProjectType.TELEWORKING,
                    ProjectBackGround.BLACK,
                    EnterpriseLogo.COUPANG,
                    ProjectStep.ANALYSIS,
                    "쇼핑몰",
                    PositionKind.DEVELOPER,
                    "Java",
                    "쇼핑몰 프로젝트" + i,
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
                    ProjectStatus.PROGRESS,
                    enterprise
            ));
            applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
            interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));
            waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));
            projectService.startProject(memberDetails, new ProjectProcessingRequest(project.getNum()));
            projectService.finishProject(memberDetails, new ProjectProcessingRequest(project.getNum()));

        }

        mockMvc.perform(get("/finish-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("finish-project-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 데이터의 타입필드, 응답 객체는 JSON 형태로 응답")
                        ),
                        responseFields(
                                fieldWithPath("[].projectNum").type("Long").description("프로젝트 식별자"),
                                fieldWithPath("[].projectName").type("String").description("프로젝트 명"),
                                fieldWithPath("[].positionKind").type("PositionKind").description("DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("[].demandCareer").type("String").description("요구 경력"),
                                fieldWithPath("[].headCount").type("int").description("모집 인원"),
                                fieldWithPath("[].projectStateDate").type("LocalDate").description("프로젝트 시작 날짜"),
                                fieldWithPath("[].projectEndDate").type("LocalDate").description("프로젝트 종료 날짜"),
                                fieldWithPath("[].minMoney").type("int").description("예상 월 단가(최소)"),
                                fieldWithPath("[].maxMoney").type("int").description("예상 월 단가(최대)"),
                                fieldWithPath("[].createdDate").type("LocalDate").description("프로젝트 등록 날짜"),
                                fieldWithPath("[].processingFreelancerCount").type("int").description("진행 프로젝트의 프리랜서 수"),
                                fieldWithPath("[].waitFreelancerDto.[].num").type("Long").description("진행 프로젝트의 프리랜서 식별자"),
                                fieldWithPath("[].waitFreelancerDto.[].name").type("String").description("진행 프로젝트의 프리랜서 이름"),
                                fieldWithPath("[].waitFreelancerDto.[].phone").type("String").description("진행 프로젝트의 프리랜서 핸드폰"),
                                fieldWithPath("[].waitFreelancerDto.[].careerYear").type("int").description("진행 프로젝트의 프리랜서 경력"),
                                fieldWithPath("[].waitFreelancerDto.[].positionType").type("PositionType").description(" DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")")
                        )

                ));
    }

    @Test
    @DisplayName("프로젝트 진행 시작 문서화 테스트")
    public void 프로젝트_진행_시작_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));

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
                ProjectStatus.PROGRESS,
                enterprise
        ));
        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
        interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));
        waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));


        ProjectProcessingRequest projectProcessingRequest = new ProjectProcessingRequest(project.getNum());

        mockMvc.perform(post("/start-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(projectProcessingRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("start-project",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("projectNum").type("Long").description("프로젝트 식별자")
                        )
                ));
    }

    @Test
    @DisplayName("프로젝트 진행 종료 문서화 테스트")
    public void 프로젝트_진행_종료_문서화() throws Exception {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberDetails memberDetails = new MemberDetails(enterprise.getNum(), enterprise.getUserId(), enterprise.getRole());
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.로그인(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));

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
                ProjectStatus.PROGRESS,
                enterprise
        ));
        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
        interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));
        waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));
        projectService.startProject(memberDetails, new ProjectProcessingRequest(project.getNum()));


        ProjectProcessingRequest projectProcessingRequest = new ProjectProcessingRequest(project.getNum());

        mockMvc.perform(post("/finish-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(projectProcessingRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("finish-project",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("projectNum").type("Long").description("프로젝트 식별자")
                        )
                ));
    }
}
