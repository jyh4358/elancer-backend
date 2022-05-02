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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerPositionDocumentTest extends DocumentBaseTest {

    @Autowired
    private AcademicRepository academicRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private ProjectHistoryRepository projectHistoryRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @DisplayName("프리랜서 프로필 소개정보 저장 문서화")
    @Test
    public void 프리랜서_프로필_개발자_포지션_저장_문서화() throws Exception {
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

}
