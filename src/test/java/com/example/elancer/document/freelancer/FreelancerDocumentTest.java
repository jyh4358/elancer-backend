package com.example.elancer.document.freelancer;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.controller.FreelancerControllerPath;
import com.example.elancer.freelancer.controller.FreelancerEnumControllerPath;
import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
import com.example.elancer.freelancer.join.controller.FreelancerJoinControllerPath;
import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerWorkType;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.model.KOSAState;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.PresentWorkState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.model.WorkType;
import com.example.elancer.freelancer.repository.FreelancerWorkTypeRepository;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.model.EnterpriseLogo;
import com.example.elancer.project.model.PositionKind;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectBackGround;
import com.example.elancer.project.model.ProjectStatus;
import com.example.elancer.project.model.ProjectStep;
import com.example.elancer.project.model.ProjectType;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.waitproject.repsitory.WaitProjectRepository;
import com.example.elancer.wishprojects.model.WishProject;
import com.example.elancer.wishprojects.repository.WishProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerDocumentTest extends DocumentBaseTest {
    @Autowired
    private FreelancerWorkTypeRepository freelancerWorkTypeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private InterviewProjectRepository interviewProjectRepository;

    @Autowired
    private WaitProjectRepository waitProjectRepository;

    @Autowired
    private ApplyProjectRepository applyProjectRepository;

    @Autowired
    private WishProjectRepository wishProjectRepository;

    @DisplayName("프리랜서 회원가입 문서화 테스트")
    @Test
    public void 프리랜서_회원가입_문서화() throws Exception {
        //given
        FreelancerJoinRequest freelancerJoinRequest = new FreelancerJoinRequest(
                "memberName",
                "memberID",
                "password",
                "password",
                "membmer@email.com",
                MailReceptionState.NOT_RECEPTION,
                "memberPhone",
                WorkPossibleState.POSSIBLE,
                PositionType.DEVELOPER,
                LocalDate.of(2022, 10, 10),
                null
        );

        //when && then
        mockMvc.perform(post(FreelancerJoinControllerPath.FREELANCER_JOIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(freelancerJoinRequest)))
                .andExpectAll(status().isCreated())
                .andDo(print())
                .andDo(document("freelancer-join",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        requestFields(
                                fieldWithPath("memberName").type("String").description("회원 성명 필드."),
                                fieldWithPath("memberId").type("String").description("회원 아이디 필드."),
                                fieldWithPath("memberPassword").type("String").description("회원 비밀번호 필드."),
                                fieldWithPath("memberPasswordCheck").type("String").description("회원 비밀번호 확인 필드."),
                                fieldWithPath("memberEmail").type("String").description("회원 이메일 필드."),
                                fieldWithPath("mailReceptionState").type("Enum").description("회원 메일 수신여부 필드."),
                                fieldWithPath("memberPhone").type("String").description("회원 휴대폰 필드."),
                                fieldWithPath("workPossibleState").type("Enum").description("회원 업무 가능여부 필드."),
                                fieldWithPath("positionType").type("Enum").description("회원 포지션 정보 필드."),
                                fieldWithPath("workStartPossibleDate").type("LocalDate").description("회원 업무가능일 필드."),
                                fieldWithPath("thumbnail").type("MultipartFile").description("회원 섬네일 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 계정 정보 수정 문서화 테스트")
    @Test
    public void 프래랜서_계정정보_수정_문서화() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerAccountCoverRequest freelancerAccountCoverRequest = new FreelancerAccountCoverRequest(
                "멤버이름",
                "패스워드",
                "패스워드",
                LocalDate.of(2000, 01, 01),
                "email@email.email.com",
                "010-0101-0101",
                "http://web.com",
                CountryType.KR,
                "경기도",
                "성남시",
                "중원구",
                Arrays.asList(FreelancerWorkType.ACCOUNTING, FreelancerWorkType.BIGDATA),
                null,
                null,
                9,
                5,
                400,
                600,
                KOSAState.NOT_POSSESS,
                MailReceptionState.RECEPTION,
                PresentWorkState.FREE_AT_COMPANY,
                HopeWorkState.AT_COMPANY,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2022, 02, 01),
                CountryType.KR,
                "seoul"
        );

        //when & then
        mockMvc.perform(put(FreelancerControllerPath.FREELANCER_ACCOUNT_INFO_UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                .content(objectMapper.writeValueAsString(freelancerAccountCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-account-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("name").type("String").description("회원 이름 정보 필드"),
                                fieldWithPath("password").type("String").description("회원 비밀번호 정보 필드"),
                                fieldWithPath("passwordCheck").type("String").description("회원 비밀번호 확인 정보 필드"),
                                fieldWithPath("birthDate").type("LocalDate").description("회원 생년월일 정보 필드"),
                                fieldWithPath("email").type("String").description("회원 이메일 정보 필드"),
                                fieldWithPath("phone").type("String").description("회원 폰번호 정보 필드"),
                                fieldWithPath("website").type("String").description("회원 웹사이트 정보 필드"),
                                fieldWithPath("countryType").type("CountryType").description("회원 거주국가 정보 필드"),
                                fieldWithPath("zipcode").type("String").description("회원 거주지 정보 필드"),
                                fieldWithPath("mainAddress").type("String").description("회원 거주지 시동구 정보 필드"),
                                fieldWithPath("detailAddress").type("String").description("회원 거주지 읍면리 정보 필드"),
                                fieldWithPath("freelancerWorkTypes").type("List<FreelancerWorkType>").description("회원 업무분야 정보 필드"),
                                fieldWithPath("workEtcField").type("String").description("회원 업무분야 직접입력 정보 필드"),
                                fieldWithPath("careerForm").type("MultipartFile").description("회원 경력기술서 정보 필드"),
                                fieldWithPath("careerYear").type("int").description("회원 경력 년수 정보 필드"),
                                fieldWithPath("careerMonth").type("int").description("회원 경력 개월수 정보 필드"),
                                fieldWithPath("hopeMonthMinPay").type("int").description("회원 희망 월단가 최소값 정보 필드"),
                                fieldWithPath("hopeMonthMaxPay").type("int").description("회원 희망 월단가 최대값 정보 필드"),
                                fieldWithPath("kosaState").type("KOSAState").description("회원 kosa보유 여부 정보 필드"),
                                fieldWithPath("mailReceptionState").type("MailReceptionState").description("회원 메일수신 여부 정보 필드"),
                                fieldWithPath("presentWorkState").type("PresentWorkState").description("회원 현재 업무상태 정보 필드"),
                                fieldWithPath("hopeWorkState").type("HopeWorkState").description("회원 희망 업무형태 정보 필드"),
                                fieldWithPath("workPossibleState").type("WorkPossibleState").description("회원 업무가능 여부 정보 필드"),
                                fieldWithPath("workStartPossibleDate").type("LocalDate").description("회원 업무가능일 정보 필드"),
                                fieldWithPath("hopeWorkCountry").type("CountryType").description("회원 희망지역 국가 정보 필드"),
                                fieldWithPath("hopeWorkCity").type("String").description("회원 희망지역 도시 정보 필드")
                        )
                ));
    }

    @DisplayName("프리랜서 계정 정보 조회 문서화 테스트")
    @Test
    public void 프리랜서_계정정보_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        List<WorkType> workTypes = freelancerWorkTypeRepository.saveAll(Arrays.asList(WorkType.createWorkType(FreelancerWorkType.ACCOUNTING, freelancer), WorkType.createWorkType(FreelancerWorkType.BIGDATA, freelancer)));

        freelancer.updateFreelancer(
                "멤버이름",
                "패스워드",
                "email@email.email.com",
                "010-0101-0101",
                "http://web.com",
                new Address(CountryType.KR, "경기도", "성남시", "중원구"),
                LocalDate.of(2000, 01, 01),
                9,
                5,
                400,
                600,
                workTypes,
                null,
                KOSAState.NOT_POSSESS,
                MailReceptionState.RECEPTION,
                PresentWorkState.FREE_AT_COMPANY,
                HopeWorkState.AT_COMPANY,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2022, 02, 01),
                CountryType.KR,
                "seoul"
        );

        Freelancer updatedFreelancer = freelancerRepository.save(freelancer);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerControllerPath.FREELANCER_ACCOUNT_INFO_FIND, updatedFreelancer.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-account-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("name").type("String").description("회원 이름 정보 필드"),
                                fieldWithPath("thumbnailPath").type("String").description("회원 섬네일 url주소 정보 필드"),
                                fieldWithPath("birthDate").type("LocalDate").description("회원 생년월일 정보 필드"),
                                fieldWithPath("email").type("String").description("회원 이메일 정보 필드"),
                                fieldWithPath("phone").type("String").description("회원 폰번호 정보 필드"),
                                fieldWithPath("website").type("String").description("회원 웹사이트 정보 필드"),
                                fieldWithPath("countryType").type("CountryType").description("회원 거주국가 정보 필드"),
                                fieldWithPath("zipcode").type("String").description("회원 거주지 정보 필드"),
                                fieldWithPath("mainAddress").type("String").description("회원 거주지 시동구 정보 필드"),
                                fieldWithPath("detailAddress").type("String").description("회원 거주지 읍면리 정보 필드"),
                                fieldWithPath("freelancerWorkTypes").type("List<FreelancerWorkType>").description("회원 업무분야 정보 필드"),
                                fieldWithPath("workEtcField").type("String").description("회원 업무분야 직접입력 정보 필드"),
                                fieldWithPath("fileName").type("String").description("회원 경력기술서 이름 정보 필드"),
                                fieldWithPath("careerYear").type("int").description("회원 경력 년수 정보 필드"),
                                fieldWithPath("careerMonth").type("int").description("회원 경력 개월수 정보 필드"),
                                fieldWithPath("hopeMonthMinPay").type("int").description("회원 희망 월단가 최소값 정보 필드"),
                                fieldWithPath("hopeMonthMaxPay").type("int").description("회원 희망 월단가 최대값 정보 필드"),
                                fieldWithPath("kosaState").type("KOSAState").description("회원 kosa보유 여부 정보 필드"),
                                fieldWithPath("mailReceptionState").type("MailReceptionState").description("회원 메일수신 여부 정보 필드"),
                                fieldWithPath("presentWorkState").type("PresentWorkState").description("회원 현재 업무상태 정보 필드"),
                                fieldWithPath("hopeWorkState").type("HopeWorkState").description("회원 희망 업무형태 정보 필드"),
                                fieldWithPath("workPossibleState").type("WorkPossibleState").description("회원 업무가능 여부 정보 필드"),
                                fieldWithPath("workStartPossibleDate").type("LocalDate").description("회원 업무가능일 정보 필드"),
                                fieldWithPath("hopeWorkCountry").type("CountryType").description("회원 희망지역 국가 정보 필드"),
                                fieldWithPath("hopeWorkCity").type("String").description("회원 희망지역 도시 정보 필드")
                        )
                ));
    }

    @DisplayName("프리랜서 수주관리 정보 조회 문서화 테스트")
    @Test
    public void 프리랜서_수주관리_정보조회_문서화() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        MemberDetails memberDetails = MemberDetails.builder()
                .id(freelancer.getNum())
                .userId(freelancer.getUserId())
                .role(freelancer.getRole())
                .build();

        Project project = projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
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

        ApplyProject applyProject = applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));

        InterviewProject interviewProject = interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));

        WaitProject waitProject = waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));

        WishProject wishProject = wishProjectRepository.save(WishProject.createWishProject(freelancer, project));

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerControllerPath.FREELANCER_OBTAIN_ORDER_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-obtainOrder-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("applyProjectCount").type("int").description("프리랜서 지원현황 갯수 정보 필드"),
                                fieldWithPath("interviewProjectCount").type("int").description("프리랜서 인터뷰요청 갯수 정보 필드"),
                                fieldWithPath("joinedProjectCount").type("int").description("프리랜서 낙찰프로젝트 갯수 정보 필드"),
                                fieldWithPath("wishProjectCount").type("int").description("프리랜서 찜프로젝트 갯수 정보 필드"),
                                fieldWithPath("applyProjectResponses").type("List<ProjectBasicResponse>").description("프리랜서 지원현황 정보 필드"),
                                fieldWithPath("applyProjectResponses.[].projectNum").type("Long").description("지원한 프로젝트 식별자"),
                                fieldWithPath("applyProjectResponses.[].projectName").type("String").description("지원한 프로젝트 명"),
                                fieldWithPath("applyProjectResponses.[].positionKind").type("PositionKind").description("지원한 프로젝트 종류 DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("applyProjectResponses.[].demandCareer").type("String").description("지원한 프로젝트 요구 경력"),
                                fieldWithPath("applyProjectResponses.[].headCount").type("int").description("지원한 프로젝트 모집 인원"),
                                fieldWithPath("applyProjectResponses.[].projectStateDate").type("LocalDate").description("지원한 프로젝트 프로젝트 시작 날짜"),
                                fieldWithPath("applyProjectResponses.[].projectEndDate").type("LocalDate").description("지원한 프로젝트 프로젝트 종료 날짜"),
                                fieldWithPath("applyProjectResponses.[].minMoney").type("int").description("지원한 프로젝트 예상 월 단가(최소)"),
                                fieldWithPath("applyProjectResponses.[].maxMoney").type("int").description("지원한 프로젝트 예상 월 단가(최대)"),
                                fieldWithPath("applyProjectResponses.[].createdDate").type("LocalDate").description("지원한 프로젝트 프로젝트 등록 날짜"),
                                fieldWithPath("interviewProjectResponses").type("List<ProjectBasicResponse>").description("프리랜서 인터뷰요청 정보 필드"),
                                fieldWithPath("interviewProjectResponses.[].projectNum").type("Long").description("지원한 프로젝트 식별자"),
                                fieldWithPath("interviewProjectResponses.[].projectName").type("String").description("지원한 프로젝트 명"),
                                fieldWithPath("interviewProjectResponses.[].positionKind").type("PositionKind").description("지원한 프로젝트 종류 DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("interviewProjectResponses.[].demandCareer").type("String").description("지원한 프로젝트 요구 경력"),
                                fieldWithPath("interviewProjectResponses.[].headCount").type("int").description("지원한 프로젝트 모집 인원"),
                                fieldWithPath("interviewProjectResponses.[].projectStateDate").type("LocalDate").description("지원한 프로젝트 프로젝트 시작 날짜"),
                                fieldWithPath("interviewProjectResponses.[].projectEndDate").type("LocalDate").description("지원한 프로젝트 프로젝트 종료 날짜"),
                                fieldWithPath("interviewProjectResponses.[].minMoney").type("int").description("지원한 프로젝트 예상 월 단가(최소)"),
                                fieldWithPath("interviewProjectResponses.[].maxMoney").type("int").description("지원한 프로젝트 예상 월 단가(최대)"),
                                fieldWithPath("interviewProjectResponses.[].createdDate").type("LocalDate").description("지원한 프로젝트 프로젝트 등록 날짜"),
                                fieldWithPath("joinedProjectResponses").type("List<ProjectBasicResponse>").description("프리랜서 낙찰프로젝트 정보 필드필드"),
                                fieldWithPath("joinedProjectResponses.[].projectNum").type("Long").description("지원한 프로젝트 식별자"),
                                fieldWithPath("joinedProjectResponses.[].projectName").type("String").description("지원한 프로젝트 명"),
                                fieldWithPath("joinedProjectResponses.[].positionKind").type("PositionKind").description("지원한 프로젝트 종류 DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("joinedProjectResponses.[].demandCareer").type("String").description("지원한 프로젝트 요구 경력"),
                                fieldWithPath("joinedProjectResponses.[].headCount").type("int").description("지원한 프로젝트 모집 인원"),
                                fieldWithPath("joinedProjectResponses.[].projectStateDate").type("LocalDate").description("지원한 프로젝트 프로젝트 시작 날짜"),
                                fieldWithPath("joinedProjectResponses.[].projectEndDate").type("LocalDate").description("지원한 프로젝트 프로젝트 종료 날짜"),
                                fieldWithPath("joinedProjectResponses.[].minMoney").type("int").description("지원한 프로젝트 예상 월 단가(최소)"),
                                fieldWithPath("joinedProjectResponses.[].maxMoney").type("int").description("지원한 프로젝트 예상 월 단가(최대)"),
                                fieldWithPath("joinedProjectResponses.[].createdDate").type("LocalDate").description("지원한 프로젝트 프로젝트 등록 날짜"),
                                fieldWithPath("wishProjectResponses").type("List<ProjectBasicResponse>").description("프리랜서 찜프로젝트 정보 필드"),
                                fieldWithPath("wishProjectResponses.[].projectNum").type("Long").description("지원한 프로젝트 식별자"),
                                fieldWithPath("wishProjectResponses.[].projectName").type("String").description("지원한 프로젝트 명"),
                                fieldWithPath("wishProjectResponses.[].positionKind").type("PositionKind").description("지원한 프로젝트 종류 DEVELOPER(\"개발자\"), PUBLISHER(\"퍼블리셔\"), DESIGNER(\"디자이너\"), PLANNER(\"기획자\"), CROWD_WORKER(\"크라우드워커\"), ETC(\"기타\")"),
                                fieldWithPath("wishProjectResponses.[].demandCareer").type("String").description("지원한 프로젝트 요구 경력"),
                                fieldWithPath("wishProjectResponses.[].headCount").type("int").description("지원한 프로젝트 모집 인원"),
                                fieldWithPath("wishProjectResponses.[].projectStateDate").type("LocalDate").description("지원한 프로젝트 프로젝트 시작 날짜"),
                                fieldWithPath("wishProjectResponses.[].projectEndDate").type("LocalDate").description("지원한 프로젝트 프로젝트 종료 날짜"),
                                fieldWithPath("wishProjectResponses.[].minMoney").type("int").description("지원한 프로젝트 예상 월 단가(최소)"),
                                fieldWithPath("wishProjectResponses.[].maxMoney").type("int").description("지원한 프로젝트 예상 월 단가(최대)"),
                                fieldWithPath("wishProjectResponses.[].createdDate").type("LocalDate").description("지원한 프로젝트 프로젝트 등록 날짜")
                        )
                ));
    }

    @DisplayName("프리랜서 국가정보 조회 문서화 테스트")
    @Test
    public void 프래랜서_국가정보_조회_문서화() throws Exception {
        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerEnumControllerPath.FREELANCER_COUNTRIES_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-countryType-names-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("countryNames").type("List<String>").description("거주국가 이름 리스트 정보 필드")
                        )
                ));
    }

    @DisplayName("프리랜서 업무분야 조회 문서화 테스트")
    @Test
    public void 프래랜서_업무분야_조회_문서화() throws Exception {
        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerEnumControllerPath.FREELANCER_WORK_TYPE_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-worktype-names-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("workTypeNames").type("List<String>").description("업무분야 이름 리스트 정보 필드")
                        )
                ));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}
