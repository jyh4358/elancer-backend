package com.example.elancer.document.freelancer;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.controller.FreelancerProfileAlterControllerPath;
import com.example.elancer.freelancerprofile.controller.FreelancerProfileFindControllerPath;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationAndLicenseAndLanguageRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.LanguageCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.LicenseCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.ProjectHistoryCoverRequest;
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
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.example.elancer.freelancerprofile.repository.academic.AcademicRepository;
import com.example.elancer.freelancerprofile.repository.career.CareerRepository;
import com.example.elancer.freelancerprofile.repository.education.EducationRepository;
import com.example.elancer.freelancerprofile.repository.language.LanguageRepository;
import com.example.elancer.freelancerprofile.repository.license.LicenseRepository;
import com.example.elancer.freelancerprofile.repository.projecthistory.ProjectHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;

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
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        IntroduceCoverRequest introduceCoverRequest = new IntroduceCoverRequest("introName", IntroBackGround.COBALT_BLUE, "introVideoUrl", "introContent");
        //when & then
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_INTRO_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(introduceCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-intro-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        requestFields(
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
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

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
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_ACADEMIC_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(academicAbilityCoverRequests)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-academic-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
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
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        CareerCoverRequest careerCoverRequest = new CareerCoverRequest(
                "companyName",
                "departmentName",
                CompanyPosition.HEAD_OF_DEPARTMENT,
                LocalDate.of(2020, 9, 01),
                LocalDate.of(2021, 10, 01)
        );

        CareerCoverRequests careerCoverRequests = new CareerCoverRequests(Arrays.asList(careerCoverRequest));

        //when & then
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_CAREER_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(careerCoverRequests)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-career-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
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
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        EducationCoverRequest educationCoverRequest = new EducationCoverRequest("우아한테크코스", "우아한형제들", LocalDate.of(2020, 01, 01), LocalDate.of(2021, 01, 01));
        LicenseCoverRequest licenseCoverRequest = new LicenseCoverRequest("정보처리기사", "한국자격증협회", LocalDate.of(2020, 05, 20));
        LanguageCoverRequest languageCoverRequest = new LanguageCoverRequest("영어", LanguageAbility.MIDDLE);

        EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests = new EducationAndLicenseAndLanguageRequests(
                Arrays.asList(educationCoverRequest),
                Arrays.asList(licenseCoverRequest),
                Arrays.asList(languageCoverRequest)
        );

        //when & then
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_EDU_AND_LICENSE_AND_LANG_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(educationAndLicenseAndLanguageRequests)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-education-license-language-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
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
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        ProjectHistoryCoverRequest projectHistoryCoverRequest = new ProjectHistoryCoverRequest(
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

        //when & then
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_PROJECT_HISTORY_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(projectHistoryCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-projecthistory-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        requestFields(
                                fieldWithPath("projectTitle").type("String").description("프리랜서 프로젝트 수행이력 프로젝트명 정보 필드."),
                                fieldWithPath("projectStartDate").type("LocalDate").description("프리랜서 프로젝트 수행이력 프로젝트 시작년월 정보 필드."),
                                fieldWithPath("projectEndDate").type("LocalDate").description("프리랜서 프로젝트 수행이력 프로젝트 종료년월 정보 필드."),
                                fieldWithPath("clientCompany").type("String").description("프리랜서 프로젝트 수행이력 고객사 정보 필드."),
                                fieldWithPath("workCompany").type("String").description("프리랜서 프로젝트 수행이력 근무사 정보 필드."),
                                fieldWithPath("developField").type("DevelopField").description("프리랜서 프로젝트 수행이력 개발분야 정보 필드."),
                                fieldWithPath("developRole").type("String").description("프리랜서 프로젝트 수행이력 역할 정보 필드."),
                                fieldWithPath("developEnvironmentModel").type("String").description("프리랜서 프로젝트 수행이력 개발환경 기종 정보 필드."),
                                fieldWithPath("developEnvironmentOS").type("String").description("프리랜서 프로젝트 수행이력 개발환경 os 정보 필드."),
                                fieldWithPath("developEnvironmentLanguage").type("String").description("프리랜서 프로젝트 수행이력 개발환경 언어 정보 필드."),
                                fieldWithPath("developEnvironmentDBName").type("String").description("프리랜서 프로젝트 수행이력 개발환경 DB 정보 필드."),
                                fieldWithPath("developEnvironmentTool").type("String").description("프리랜서 프로젝트 수행이력 개발환경 툴 정보 필드."),
                                fieldWithPath("developEnvironmentCommunication").type("String").description("프리랜서 프로젝트 수행이력 개발환경 통신 정보 필드."),
                                fieldWithPath("developEnvironmentEtc").type("String").description("프리랜서 프로젝트 수행이력 개발환경 기타 정보 필드."),
                                fieldWithPath("responsibilityTask").type("String").description("프리랜서 프로젝트 수행이력 담당업무 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 세부정보 조회 문서화")
    @Test
    public void 프리랜서_프로필_세부정보_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

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
        freelancerProfile.coverIntroduceInFreelancer(introduceName, introBackGround, introduceVideoURL, introduceContent);

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.plusProjectHistory(projectHistory);
        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_DETAIL, freelancer.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-detail-find",
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
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

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
        freelancerProfile.coverIntroduceInFreelancer(introduceName, introBackGround, introduceVideoURL, introduceContent);

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.plusProjectHistory(projectHistory);
        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        String path = FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_SIMPLE.replace("{freelancerNum}", String.valueOf(freelancer.getNum()));
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_SIMPLE, freelancer.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-simple-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("profileNum").type("Long").description("프리랜서 프로필 식별자 정보 필드."),
                                fieldWithPath("name").type("String").description("프리랜서 이름 정보 필드."),
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
                                fieldWithPath("projectHistoryResponses.[0].responsibilityTask").type("String").description("프리랜서 프로젝트 수행이력 담당업무 정보 필드.")
                        )
                ));
    }

    @AfterEach
    void tearDown() {
        databaseClean.clean();
    }
}
