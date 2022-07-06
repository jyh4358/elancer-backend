package com.example.elancer.document.freelancer;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancerprofile.controller.position.FreelancerPositionEnumControllerPath;
import com.example.elancer.freelancerprofile.controller.profile.FreelancerProfileAlterControllerPath;
import com.example.elancer.freelancerprofile.controller.profile.FreelancerProfileFindControllerPath;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationAndLicenseAndLanguageRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.LanguageCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.LicenseCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.ProjectHistoryCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.ProjectHistoryRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import com.example.elancer.freelancerprofile.model.education.Education;
import com.example.elancer.freelancerprofile.model.language.Language;
import com.example.elancer.freelancerprofile.model.language.LanguageAbility;
import com.example.elancer.freelancerprofile.model.license.License;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.domain.MemberType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerProfileDocumentTest extends DocumentBaseTest {

    @DisplayName("프리랜서 프로필 소개정보 저장 문서화")
    @Test
    public void 프리랜서_프로필_소개정보_저장_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        IntroduceCoverRequest introduceCoverRequest = new IntroduceCoverRequest("greeting","introName", IntroBackGround.COBALT_BLUE, "introVideoUrl", "introContent");

        //when & then
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_INTRO_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                .content(objectMapper.writeValueAsString(introduceCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-intro-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("greeting").type("String").description("프리랜서 프로필 한줄소개 정보 필드."),
                                fieldWithPath("introName").type("String").description("프리랜서 소개이름 정보 필드."),
                                fieldWithPath("introBackGround").type("IntroBackGround").description("프리랜서 소개배경 정보 필드."),
                                fieldWithPath("introVideoUrl").type("String").description("프리랜서 소개영상 URL 정보 필드."),
                                fieldWithPath("introContent").type("String").description("프리랜서 소개글 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 학력정보 저장 문서화")
    @Test
    public void 프리랜서_프로필_학력정보_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        AcademicAbilityCoverRequest academicAbilityCoverRequest = new AcademicAbilityCoverRequest(
                "schoolName",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2015, 02, 01),
                LocalDate.of(2018, 02, 01),
                AcademicState.GRADUATION,
                "문과"
        );

        AcademicAbilityCoverRequests academicAbilityCoverRequests = new AcademicAbilityCoverRequests(Arrays.asList(academicAbilityCoverRequest));

        //when & then
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_ACADEMIC_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(academicAbilityCoverRequests)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-academic-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("academicAbilityCoverRequests.[0].schoolName").type("String").description("프리랜서 학력정보 학교명 정보 필드."),
                                fieldWithPath("academicAbilityCoverRequests.[0].schoolLevel").type("SchoolLevel").description("프리랜서 학력정보 학력단계 정보 필드."),
                                fieldWithPath("academicAbilityCoverRequests.[0].enterSchoolDate").type("LocalDate").description("프리랜서 학력정보 입학일 정보 필드."),
                                fieldWithPath("academicAbilityCoverRequests.[0].graduationDate").type("LocalDate").description("프리랜서 학력정보 졸업일 정보 필드."),
                                fieldWithPath("academicAbilityCoverRequests.[0].academicState").type("AcademicState").description("프리랜서 학력정보 현재상태 정보 필드."),
                                fieldWithPath("academicAbilityCoverRequests.[0].majorName").type("String").description("프리랜서 학력정보 전공 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 근무경력 저장 문서화")
    @Test
    public void 프리랜서_프로필_근무경력_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        CareerCoverRequest careerCoverRequest = new CareerCoverRequest(
                "companyName",
                "departmentName",
                CompanyPosition.HEAD_OF_DEPARTMENT,
                LocalDate.of(2020, 9, 01),
                LocalDate.of(2021, 10, 01)
        );

        CareerCoverRequests careerCoverRequests = new CareerCoverRequests(Arrays.asList(careerCoverRequest));

        //when & then
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_CAREER_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(careerCoverRequests)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-career-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("careerCoverRequests.[0].companyName").type("String").description("프리랜서 근무경력 회사명 정보 필드."),
                                fieldWithPath("careerCoverRequests.[0].departmentName").type("String").description("프리랜서 근무경력 부서명 정보 필드."),
                                fieldWithPath("careerCoverRequests.[0].companyPosition").type("CompanyPosition").description("프리랜서 근무경력 직책 정보 필드."),
                                fieldWithPath("careerCoverRequests.[0].careerStartDate").type("LocalDate").description("프리랜서 근무경력 근무시작월 정보 필드."),
                                fieldWithPath("careerCoverRequests.[0].careerEndDate").type("LocalDate").description("프리랜서 근무경력 퇴사월 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 교육 및 자격사항 저장 문서화")
    @Test
    public void 프리랜서_프로필_교육_및_자격사항_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        EducationCoverRequest educationCoverRequest = new EducationCoverRequest("우아한테크코스", "우아한형제들", LocalDate.of(2020, 01, 01), LocalDate.of(2021, 01, 01));
        LicenseCoverRequest licenseCoverRequest = new LicenseCoverRequest("정보처리기사", "한국자격증협회", LocalDate.of(2020, 05, 20));
        LanguageCoverRequest languageCoverRequest = new LanguageCoverRequest("영어", LanguageAbility.MIDDLE);

        EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests = new EducationAndLicenseAndLanguageRequests(
                Arrays.asList(educationCoverRequest),
                Arrays.asList(licenseCoverRequest),
                Arrays.asList(languageCoverRequest)
        );

        //when & then
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_EDU_AND_LICENSE_AND_LANG_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(educationAndLicenseAndLanguageRequests)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-education-license-language-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("educationCoverRequests.[0].educationTitle").type("String").description("프리랜서 교육 교육명 정보 필드."),
                                fieldWithPath("educationCoverRequests.[0].educationOrganization").type("String").description("프리랜서 교육 교육기관명 정보 필드."),
                                fieldWithPath("educationCoverRequests.[0].educationStartDate").type("LocalDate").description("프리랜서 교육 교육시작년월 정보 필드."),
                                fieldWithPath("educationCoverRequests.[0].educationEndDate").type("LocalDate").description("프리랜서 교육 교육수료년월 정보 필드."),
                                fieldWithPath("licenseCoverRequests.[0].licenseTitle").type("String").description("프리랜서 자격증 자격증명 정보 필드."),
                                fieldWithPath("licenseCoverRequests.[0].licenseIssuer").type("String").description("프리랜서 자격증 발급기관 정보 필드."),
                                fieldWithPath("licenseCoverRequests.[0].acquisitionDate").type("LocalDate").description("프리랜서 자격증 취득년월 정보 필드."),
                                fieldWithPath("languageCoverRequests.[0].languageName").type("String").description("프리랜서 외국어 외국어명 정보 필드."),
                                fieldWithPath("languageCoverRequests.[0].languageAbility").type("LanguageAbility").description("프리랜서 외국어 실력 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 프로젝트 수행이력 저장 문서화")
    @Test
    public void 프리랜서_프로필_프로젝트_수행이력_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        ProjectHistoryRequest projectHistoryRequest1 = new ProjectHistoryRequest(
                "projectTitle",
                LocalDate.of(2020, 12, 01),
                LocalDate.of(2021, 9, 01),
                "clientCompany",
                "workCompany",
                DevelopField.APPLICATION,
                "백엔드 개발자",
                "model",
                "OS",
                "language",
                "mysql",
                "tool",
                "communication",
                null,
                "담당업무"
        );

        ProjectHistoryRequest projectHistoryRequest2 = new ProjectHistoryRequest(
                "projectTitle2",
                LocalDate.of(2020, 11, 01),
                LocalDate.of(2021, 8, 01),
                "clientCompany2",
                "workCompany2",
                DevelopField.APPLICATION,
                "프론트엔드 개발자",
                "model2",
                "OS2",
                "language2",
                "mysql2",
                "tool2",
                "communication2",
                null,
                "담당업무2"
        );

        ProjectHistoryCoverRequests projectHistoryCoverRequest = new ProjectHistoryCoverRequests(Arrays.asList(projectHistoryRequest1, projectHistoryRequest2));

        //when & then
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_PROJECT_HISTORY_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(projectHistoryCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-projecthistory-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("projectHistoryRequestList.[0].projectTitle").type("String").description("프리랜서 프로젝트 수행이력 프로젝트명 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].projectStartDate").type("LocalDate").description("프리랜서 프로젝트 수행이력 프로젝트 시작년월 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].projectEndDate").type("LocalDate").description("프리랜서 프로젝트 수행이력 프로젝트 종료년월 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].clientCompany").type("String").description("프리랜서 프로젝트 수행이력 고객사 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].workCompany").type("String").description("프리랜서 프로젝트 수행이력 근무사 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].developField").type("DevelopField").description("프리랜서 프로젝트 수행이력 개발분야 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].developRole").type("String").description("프리랜서 프로젝트 수행이력 역할 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].developEnvironmentModel").type("String").description("프리랜서 프로젝트 수행이력 개발환경 기종 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].developEnvironmentOS").type("String").description("프리랜서 프로젝트 수행이력 개발환경 os 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].developEnvironmentLanguage").type("String").description("프리랜서 프로젝트 수행이력 개발환경 언어 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].developEnvironmentDBName").type("String").description("프리랜서 프로젝트 수행이력 개발환경 DB 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].developEnvironmentTool").type("String").description("프리랜서 프로젝트 수행이력 개발환경 툴 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].developEnvironmentCommunication").type("String").description("프리랜서 프로젝트 수행이력 개발환경 통신 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].developEnvironmentEtc").type("String").description("프리랜서 프로젝트 수행이력 개발환경 기타 정보 필드."),
                                fieldWithPath("projectHistoryRequestList.[0].responsibilityTask").type("String").description("프리랜서 프로젝트 수행이력 담당업무 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 세부정보 조회 문서화")
    @Test
    public void 프리랜서_프로필_세부정보_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        AcademicAbility academicAbility = AcademicAbility.createAcademicAbility(
                "고등학교",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2012, 02, 01),
                LocalDate.of(2015, 02, 01),
                AcademicState.GRADUATION,
                "문과"
        );

        Career career = Career.createCareer(
                "삼성",
                "개발팀",
                CompanyPosition.ASSISTANT_MANAGER,
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        Education education = Education.createEducation(
                "특수교육",
                "특수기관",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        License license = License.createLicense("특수 자격증", "특수 기관", LocalDate.of(2019, 02, 22));

        Language language = Language.createLanguage("영어", LanguageAbility.MIDDLE);

        ProjectHistory projectHistory = ProjectHistory.createProjectHistory(
                "프로젝트명",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01),
                "고객사명",
                "상주사명",
                DevelopField.APPLICATION,
                "backend",
                DevelopEnvironment.of(
                        "model",
                        "Ms",
                        "language",
                        "DB",
                        "Tool",
                        "통신",
                        "기타"
                ),
                "담당업무는 백엔드 개발"
        );

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java", "role");

        String introduceName = "소개글";
        IntroBackGround introBackGround = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL = "소개 영상 주소";
        String introduceContent = "소개 내용";
        freelancerProfile.coverIntroduceInFreelancer(freelancerProfile.getGreeting(), introduceName, introBackGround, introduceVideoURL, introduceContent);

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.coverProjectHistory(Arrays.asList(projectHistory));
        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_DETAIL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-detail-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("profileNum").type("Long").description("프리랜서 프로필 식별자 정보 필드."),
                                fieldWithPath("greeting").type("String").description("프리랜서 프로필 인사말 정보 필드."),
                                fieldWithPath("positionType").type("PositionType").description("프리랜서 프로필 포지션(스킬) 정보 필드."),
                                fieldWithPath("introduceResponse.introduceName").type("String").description("프리랜서 프로필 소개이름 정보 필드."),
                                fieldWithPath("introduceResponse.introBackGround").type("IntroBackGround").description("프리랜서 프로필 소개배경 정보 필드."),
                                fieldWithPath("introduceResponse.introduceVideoURL").type("String").description("프리랜서 프로필 소개영상 URL 정보 필드."),
                                fieldWithPath("introduceResponse.introduceContent").type("String").description("프리랜서 프로필 소개 글 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].schoolName").type("String").description("프리랜서 학력정보 학교명 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].schoolLevel").type("SchoolLevel").description("프리랜서 학력정보 학력단계 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].schoolLevelDescription").type("String").description("프리랜서 학력정보 학력단계 설명 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].enterSchoolDate").type("LocalDate").description("프리랜서 학력정보 입학일 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].graduationDate").type("LocalDate").description("프리랜서 학력정보 졸업일 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].academicState").type("AcademicState").description("프리랜서 학력정보 현재상태 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].majorName").type("String").description("프리랜서 학력정보 전공 정보 필드."),
                                fieldWithPath("careerResponses.[0].companyName").type("String").description("프리랜서 근무경력 회사명 정보 필드."),
                                fieldWithPath("careerResponses.[0].departmentName").type("String").description("프리랜서 근무경력 부서명 정보 필드."),
                                fieldWithPath("careerResponses.[0].companyPosition").type("CompanyPosition").description("프리랜서 근무경력 직책 정보 필드."),
                                fieldWithPath("careerResponses.[0].companyPositionDescription").type("String").description("프리랜서 근무경력 직책 설명 정보 필드."),
                                fieldWithPath("careerResponses.[0].careerStartDate").type("LocalDate").description("프리랜서 근무경력 근무시작월 정보 필드."),
                                fieldWithPath("careerResponses.[0].careerEndDate").type("LocalDate").description("프리랜서 근무경력 퇴사월 정보 필드."),
                                fieldWithPath("educationResponses.[0].educationTitle").type("String").description("프리랜서 교육 교육명 정보 필드."),
                                fieldWithPath("educationResponses.[0].educationOrganization").type("String").description("프리랜서 교육 교육기관명 정보 필드."),
                                fieldWithPath("educationResponses.[0].educationStartDate").type("LocalDate").description("프리랜서 교육 교육시작년월 정보 필드."),
                                fieldWithPath("educationResponses.[0].educationEndDate").type("LocalDate").description("프리랜서 교육 교육수료년월 정보 필드."),
                                fieldWithPath("licenseResponses.[0].licenseTitle").type("String").description("프리랜서 자격증 자격증명 정보 필드."),
                                fieldWithPath("licenseResponses.[0].licenseIssuer").type("String").description("프리랜서 자격증 발급기관 정보 필드."),
                                fieldWithPath("licenseResponses.[0].acquisitionDate").type("LocalDate").description("프리랜서 자격증 취득년월 정보 필드."),
                                fieldWithPath("languageResponses.[0].languageName").type("String").description("프리랜서 외국어 외국어명 정보 필드."),
                                fieldWithPath("languageResponses.[0].languageAbility").type("LanguageAbility").description("프리랜서 외국어 실력 정보 필드."),
                                fieldWithPath("languageResponses.[0].languageAbilityDescription").type("LanguageAbility").description("프리랜서 외국어 실력 설명 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].projectTitle").type("String").description("프리랜서 프로젝트 수행이력 프로젝트명 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].projectStartDate").type("LocalDate").description("프리랜서 프로젝트 수행이력 프로젝트 시작년월 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].projectEndDate").type("LocalDate").description("프리랜서 프로젝트 수행이력 프로젝트 종료년월 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].clientCompany").type("String").description("프리랜서 프로젝트 수행이력 고객사 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].workCompany").type("String").description("프리랜서 프로젝트 수행이력 근무사 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developField").type("DevelopField").description("프리랜서 프로젝트 수행이력 개발분야 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developRole").type("String").description("프리랜서 프로젝트 수행이력 역할 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentModel").type("String").description("프리랜서 프로젝트 수행이력 개발환경 기종 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentOS").type("String").description("프리랜서 프로젝트 수행이력 개발환경 os 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentLanguage").type("String").description("프리랜서 프로젝트 수행이력 개발환경 언어 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentDBName").type("String").description("프리랜서 프로젝트 수행이력 개발환경 DB 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentTool").type("String").description("프리랜서 프로젝트 수행이력 개발환경 툴 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentCommunication").type("String").description("프리랜서 프로젝트 수행이력 개발환경 통신 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentEtc").type("String").description("프리랜서 프로젝트 수행이력 개발환경 기타 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].responsibilityTask").type("String").description("프리랜서 프로젝트 수행이력 담당업무 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 요약정보 조회 문서화")
    @Test
    public void 프리랜서_프로필_요약정보_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer , PositionType.DEVELOPER));

        AcademicAbility academicAbility = AcademicAbility.createAcademicAbility(
                "고등학교",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2012, 02, 01),
                LocalDate.of(2015, 02, 01),
                AcademicState.GRADUATION,
                "문과"
        );

        Career career = Career.createCareer(
                "삼성",
                "개발팀",
                CompanyPosition.ASSISTANT_MANAGER,
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        Education education = Education.createEducation(
                "특수교육",
                "특수기관",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        License license = License.createLicense("특수 자격증", "특수 기관", LocalDate.of(2019, 02, 22));

        Language language = Language.createLanguage("영어", LanguageAbility.MIDDLE);

        ProjectHistory projectHistory = ProjectHistory.createProjectHistory(
                "프로젝트명",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01),
                "고객사명",
                "상주사명",
                DevelopField.APPLICATION,
                "backend",
                DevelopEnvironment.of(
                        "model",
                        "Ms",
                        "language",
                        "DB",
                        "Tool",
                        "통신",
                        "기타"
                ),
                "담당업무는 백엔드 개발"
        );

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java", "role");

        String introduceName = "소개글";
        IntroBackGround introBackGround = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL = "소개 영상 주소";
        String introduceContent = "소개 내용";
        freelancerProfile.coverIntroduceInFreelancer(freelancerProfile.getGreeting(),introduceName, introBackGround, introduceVideoURL, introduceContent);

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.coverProjectHistory(Arrays.asList(projectHistory));
        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        String path = FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_SIMPLE.replace("{freelancerNum}", String.valueOf(freelancer.getNum()));
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_SIMPLE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-simple-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("profileNum").type("Long").description("프리랜서 프로필 식별자 정보 필드."),
                                fieldWithPath("name").type("String").description("프리랜서 이름 정보 필드."),
                                fieldWithPath("thumbnailPath").type("String").description("프리랜서 섬네일 url주소 정보 필드."),
                                fieldWithPath("expertise").type("int").description("프리랜서 활동평가 전문성 정보 필드."),
                                fieldWithPath("scheduleAdherence").type("int").description("프리랜서 활동평가 일정준수 정보 필드."),
                                fieldWithPath("initiative").type("int").description("프리랜서 활동평가 적극성 정보 필드."),
                                fieldWithPath("communication").type("int").description("프리랜서 활동평가 의사소통 정보 필드."),
                                fieldWithPath("reemploymentIntention").type("int").description("프리랜서 활동평가 재고용의사 정보 필드."),
                                fieldWithPath("totalActiveScore").type("Double").description("프리랜서 활동평가 총 평점 정보 필드."),
                                fieldWithPath("introduceName").type("String").description("프리랜서 프로필 소개이름 정보 필드."),
                                fieldWithPath("introBackGround").type("IntroBackGround").description("프리랜서 프로필 소개배경 정보 필드."),
                                fieldWithPath("greeting").type("String").description("프리랜서 프로필 인사말 정보 필드."),
                                fieldWithPath("careerYear").type("int").description("프리랜서 경력년수 정보 필드."),
                                fieldWithPath("positionType").type("PositionType").description("프리랜서 프로필 포지션(스킬) 정보 필드."),
                                fieldWithPath("positionTypeDescription").type("String").description("프리랜서 프로필 포지션(스킬) 설명 정보 필드."),
                                fieldWithPath("introduceVideoUrl").type("String").description("프리랜서 프로필 소개영상 URL 정보 필드."),
                                fieldWithPath("introduceContent").type("String").description("프리랜서 프로필 소개 글 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].schoolName").type("String").description("프리랜서 학력정보 학교명 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].schoolLevel").type("SchoolLevel").description("프리랜서 학력정보 학력단계 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].schoolLevelDescription").type("String").description("프리랜서 학력정보 학력단계 설명 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].enterSchoolDate").type("LocalDate").description("프리랜서 학력정보 입학일 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].graduationDate").type("LocalDate").description("프리랜서 학력정보 졸업일 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].academicState").type("AcademicState").description("프리랜서 학력정보 현재상태 정보 필드."),
                                fieldWithPath("academicAbilityResponses.[0].majorName").type("String").description("프리랜서 학력정보 전공 정보 필드."),
                                fieldWithPath("careerResponses.[0].companyName").type("String").description("프리랜서 근무경력 회사명 정보 필드."),
                                fieldWithPath("careerResponses.[0].departmentName").type("String").description("프리랜서 근무경력 부서명 정보 필드."),
                                fieldWithPath("careerResponses.[0].companyPosition").type("CompanyPosition").description("프리랜서 근무경력 직책 정보 필드."),
                                fieldWithPath("careerResponses.[0].companyPositionDescription").type("String").description("프리랜서 근무경력 직책 설명 정보 필드."),
                                fieldWithPath("careerResponses.[0].careerStartDate").type("LocalDate").description("프리랜서 근무경력 근무시작월 정보 필드."),
                                fieldWithPath("careerResponses.[0].careerEndDate").type("LocalDate").description("프리랜서 근무경력 퇴사월 정보 필드."),
                                fieldWithPath("educationResponses.[0].educationTitle").type("String").description("프리랜서 교육 교육명 정보 필드."),
                                fieldWithPath("educationResponses.[0].educationOrganization").type("String").description("프리랜서 교육 교육기관명 정보 필드."),
                                fieldWithPath("educationResponses.[0].educationStartDate").type("LocalDate").description("프리랜서 교육 교육시작년월 정보 필드."),
                                fieldWithPath("educationResponses.[0].educationEndDate").type("LocalDate").description("프리랜서 교육 교육수료년월 정보 필드."),
                                fieldWithPath("licenseResponses.[0].licenseTitle").type("String").description("프리랜서 자격증 자격증명 정보 필드."),
                                fieldWithPath("licenseResponses.[0].licenseIssuer").type("String").description("프리랜서 자격증 발급기관 정보 필드."),
                                fieldWithPath("licenseResponses.[0].acquisitionDate").type("LocalDate").description("프리랜서 자격증 취득년월 정보 필드."),
                                fieldWithPath("languageResponses.[0].languageName").type("String").description("프리랜서 외국어 외국어명 정보 필드."),
                                fieldWithPath("languageResponses.[0].languageAbility").type("LanguageAbility").description("프리랜서 외국어 실력 정보 필드."),
                                fieldWithPath("languageResponses.[0].languageAbilityDescription").type("LanguageAbility").description("프리랜서 외국어 실력 설명 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].projectTitle").type("String").description("프리랜서 프로젝트 수행이력 프로젝트명 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].projectStartDate").type("LocalDate").description("프리랜서 프로젝트 수행이력 프로젝트 시작년월 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].projectEndDate").type("LocalDate").description("프리랜서 프로젝트 수행이력 프로젝트 종료년월 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].clientCompany").type("String").description("프리랜서 프로젝트 수행이력 고객사 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].workCompany").type("String").description("프리랜서 프로젝트 수행이력 근무사 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developField").type("DevelopField").description("프리랜서 프로젝트 수행이력 개발분야 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developRole").type("String").description("프리랜서 프로젝트 수행이력 역할 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentModel").type("String").description("프리랜서 프로젝트 수행이력 개발환경 기종 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentOS").type("String").description("프리랜서 프로젝트 수행이력 개발환경 os 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentLanguage").type("String").description("프리랜서 프로젝트 수행이력 개발환경 언어 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentDBName").type("String").description("프리랜서 프로젝트 수행이력 개발환경 DB 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentTool").type("String").description("프리랜서 프로젝트 수행이력 개발환경 툴 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentCommunication").type("String").description("프리랜서 프로젝트 수행이력 개발환경 통신 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].developEnvironment.developEnvironmentEtc").type("String").description("프리랜서 프로젝트 수행이력 개발환경 기타 정보 필드."),
                                fieldWithPath("projectHistoryResponses.[0].responsibilityTask").type("String").description("프리랜서 프로젝트 수행이력 담당업무 정보 필드."),
                                fieldWithPath("allSkillNames.[0]").type("String").description("모든 스킬")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 인덱스 페이지 리스트 조회 문서화")
    @Test
    public void 프리랜서_프로필_인덱스페이지_리스트조회_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(Freelancer.createFreelancer(
                "userId",
                "password",
                "name",
                "phone",
                "email",
                null,
                new Address(CountryType.KR,
                        "경기",
                        "성남",
                        "판교"
                ),
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2020, 02, 01)
        ));

        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        freelancerProfileRepository.save(freelancerProfile);

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java", "role");

        String introduceName = "소개글";
        IntroBackGround introBackGround = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL = "소개 영상 주소";
        String introduceContent = "소개 내용";
        freelancerProfile.coverIntroduceInFreelancer(freelancerProfile.getGreeting(), introduceName, introBackGround, introduceVideoURL, introduceContent);
        freelancerProfile.coverPosition(developer);
        freelancerProfileRepository.save(freelancerProfile);

        Freelancer freelancer2 = freelancerRepository.save(Freelancer.createFreelancer(
                "userId2",
                "password2",
                "name2",
                "phone2",
                "email2",
                null,
                new Address(CountryType.KR,
                        "경기",
                        "성남",
                        "판교2"
                ),
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2020, 02, 01)
        ));

        FreelancerProfile freelancerProfile2 = new FreelancerProfile("greeting", freelancer2, PositionType.PLANNER);
        freelancerProfileRepository.save(freelancerProfile2);

        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile2);

        String introduceName2 = "소개글";
        IntroBackGround introBackGround2 = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL2 = "소개 영상 주소";
        String introduceContent2 = "소개 내용";
        freelancerProfile2.coverIntroduceInFreelancer(freelancerProfile2.getGreeting(), introduceName2, introBackGround2, introduceVideoURL2, introduceContent2);
        freelancerProfile2.coverPosition(planner);
        freelancerProfileRepository.save(freelancerProfile2);

        //when & then
        mockMvc.perform(get(FreelancerProfileFindControllerPath.FREELANCER_FINDS)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-finds",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("freelancerSimpleResponseList").type("List<String>").description("프리랜서 요약 정보 데이터 리스트."),
                                fieldWithPath("freelancerSimpleResponseList.[0].freelancerNum").type("Long").description("프리랜서 식별자 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].positionName").type("String").description("프리랜서 포지션이름 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].freelancerName").type("String").description("프리랜서 이름 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].introBackGround").type("IntroBackGround").description("프리랜서 프로필 배경화면 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].careerYear").type("int").description("프리랜서 경력 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].wishState").type("boolean").description("사용자의 프리랜서 스크랩여부 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].greeting").type("String").description("프리랜서 소개말 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].skills").type("List<String>").description("프리랜서 개발자 주요스킬 정보 필드."),
                                fieldWithPath("freelancerSimpleResponseList.[0].projectNames").type("ListL<String>").description("프리랜서 개발자 기타스킬 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 개발자 스킬 & 경험 조회 문서화")
    @Test
    public void 프리랜서_프로필_개발자_스킬_경험_조회_문서화() throws Exception {
        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionEnumControllerPath.FREELANCER_POSITION_DEVELOPER_SKILLS_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-developer-skills-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("javaDetailSkillNames").type("List<String>").description("프리랜서 프로필 개발자 java 스킬 정보 필드."),
                                fieldWithPath("mobileDetailSkillNames").type("List<String>").description("프리랜서 프로필 개발자 mobile 스킬 정보 필드."),
                                fieldWithPath("phpOrAspDetailSkillNames").type("List<String>").description("프리랜서 프로필 개발자 php/asp 스킬 정보 필드."),
                                fieldWithPath("dotNetDetailSkillNames").type("List<String>").description("프리랜서 프로필 개발자 .net 스킬 정보 필드."),
                                fieldWithPath("javaScriptDetailSkillNames").type("List<String>").description("프리랜서 프로필 개발자 javaScript 스킬 정보 필드."),
                                fieldWithPath("cdetailSkillNames").type("List<String>").description("프리랜서 프로필 개발자 c언어 스킬 정보 필드."),
                                fieldWithPath("dbDetailSkillNames").type("List<String>").description("프리랜서 프로필 개발자 db 스킬 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 이넘 네임리스트 조회 문서화")
    @Test
    public void 프리랜서_프로필_이넘_네임리스트_조회_문서화() throws Exception {
        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionEnumControllerPath.FREELANCER_PROFILE_ENUMS_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-enum-names-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("schoolLevelNames").type("List<String>").description("프리랜서 프로필 학력단계 정보 필드."),
                                fieldWithPath("academicStateNames").type("List<String>").description("프리랜서 프로필 학력상태 정보 필드."),
                                fieldWithPath("companyPositionNames").type("List<String>").description("프리랜서 프로필 회사직책 정보 필드."),
                                fieldWithPath("languageAbilityNames").type("List<String>").description("프리랜서 프로필 언어구사능력 정보 필드.")
                        )
                ));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}
