package com.example.elancer.integrate.freelancerprofile;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.controller.FreelancerEnumControllerPath;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.controller.FreelancerPositionEnumControllerPath;
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
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.academic.AcademicRepository;
import com.example.elancer.freelancerprofile.repository.career.CareerRepository;
import com.example.elancer.freelancerprofile.repository.education.EducationRepository;
import com.example.elancer.freelancerprofile.repository.language.LanguageRepository;
import com.example.elancer.freelancerprofile.repository.license.LicenseRepository;
import com.example.elancer.freelancerprofile.repository.projecthistory.ProjectHistoryRepository;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.integrate.freelancer.LoginHelper;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerProfileIntegrateTest extends IntegrateBaseTest {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

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

    @DisplayName("프리랜서 프로필 소개정보 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_소개정보_저장() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        IntroduceCoverRequest introduceCoverRequest = new IntroduceCoverRequest("introName", IntroBackGround.COBALT_BLUE, "introVideoUrl", "introContent");

        //when
        프리랜서_프로필_소개정보_저장_요청(freelancerProfile, introduceCoverRequest, memberLoginResponse);

        //then
        프리랜서_프로필_소개정보_저장_요청결과_검증(freelancerProfile, introduceCoverRequest);
    }

    @DisplayName("프리랜서 프로필 학력정보 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_학력정보_저장() throws Exception {
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

        //when
        프리랜서_프로필_학력사항_저장_요청(freelancerProfile, academicAbilityCoverRequests, memberLoginResponse);

        //then
        프리랜서_프로필_학력사항_저장_요청결과_검증(academicAbilityCoverRequest);
    }

    @DisplayName("프리랜서 프로필 경력 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_경력정보_저장() throws Exception {
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

        //when
        프리랜서_프로필_경력사항_저장_요청(freelancerProfile, careerCoverRequests, memberLoginResponse);

        //then
        프리랜서_프로필_경력사항_저장_요청결과_검증(careerCoverRequests.getCareerCoverRequests().get(0));
    }

    @DisplayName("프리랜서 프로필 프로젝트이력 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_프로젝트이력_저장() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

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

        //when
        프리랜서_프로필_프로젝트이력_저장_요청(freelancerProfile, projectHistoryCoverRequest, memberLoginResponse);

        //then
        프리랜서_프로필_프로젝트이력_저장_요청결과_검증(projectHistoryCoverRequest);
    }

    @DisplayName("프리랜서 프로필 교육 및 자격사항 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_교육및자격사항_저장() throws Exception {
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

        //when
        프리랜서_프로필_교육및자격사항_저장_요청(freelancerProfile, educationAndLicenseAndLanguageRequests, memberLoginResponse);

        //then
        프리랜서_프로필_교육및자격사항_저장_요청결과_검증(educationCoverRequest, licenseCoverRequest, languageCoverRequest);
    }

    @DisplayName("프리랜서 프로필 세부사항 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_세부사항_조회() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        AcademicAbility academicAbility = AcademicAbility.createAcademicAbility(
                "고등학교",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2012, 02, 01),
                LocalDate.of(2015, 02, 01),
                AcademicState.GRADUATION,
                "문과"
        );

        AcademicAbility academicAbility2 = AcademicAbility.createAcademicAbility(
                "대학교",
                SchoolLevel.UNIVERSITY,
                LocalDate.of(2015, 02, 01),
                LocalDate.of(2020, 02, 01),
                AcademicState.GRADUATION,
                "컴퓨터공학과"
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

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility, academicAbility2));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.plusProjectHistory(projectHistory);
        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(get(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_DETAIL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("profileNum").value("1"))
                .andDo(print());

    }

    @DisplayName("프리랜서 프로필 기본 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_기본_조회() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        AcademicAbility academicAbility = AcademicAbility.createAcademicAbility(
                "고등학교",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2012, 02, 01),
                LocalDate.of(2015, 02, 01),
                AcademicState.GRADUATION,
                "문과"
        );

        AcademicAbility academicAbility2 = AcademicAbility.createAcademicAbility(
                "대학교",
                SchoolLevel.UNIVERSITY,
                LocalDate.of(2015, 02, 01),
                LocalDate.of(2020, 02, 01),
                AcademicState.GRADUATION,
                "컴퓨터공학과"
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

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility, academicAbility2));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.plusProjectHistory(projectHistory);
        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        String path = FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_SIMPLE.replace("{freelancerNum}", String.valueOf(freelancer.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(freelancer.getName()))
                .andDo(print());

    }

    @DisplayName("프리랜서 프로필 개발자 스킬리스트 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_개발자_스킬리스트_조회() throws Exception {
        //when & then
        mockMvc.perform(get(FreelancerPositionEnumControllerPath.FREELANCER_POSITION_DEVELOPER_SKILLS_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("javaDetailSkillNames.[0]").value("Front-End"))
                .andExpect(jsonPath("mobileDetailSkillNames.[0]").value("Hybrid"))
                .andExpect(jsonPath("phpOrAspDetailSkillNames.[0]").value("PHP"))
                .andExpect(jsonPath("dotNetDetailSkillNames.[0]").value("ASP.net"))
                .andExpect(jsonPath("javaScriptDetailSkillNames.[0]").value("node.js"))
                .andExpect(jsonPath("cdetailSkillNames.[0]").value("Server"))
                .andExpect(jsonPath("dbDetailSkillNames.[0]").value("Oracle"))
                .andDo(print());

    }

    @DisplayName("프리랜서 프로필 이넘리스트 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_이넘리스트_조회() throws Exception {
        //when & then
        mockMvc.perform(get(FreelancerPositionEnumControllerPath.FREELANCER_PROFILE_ENUMS_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("schoolLevelNames.[0]").value("고등학교"))
                .andExpect(jsonPath("academicStateNames.[0]").value("재학"))
                .andExpect(jsonPath("companyPositionNames.[0]").value("회장"))
                .andExpect(jsonPath("languageAbilityNames.[0]").value("일상회화 가능"))
                .andDo(print());

    }

    private void 프리랜서_프로필_소개정보_저장_요청(FreelancerProfile freelancerProfile, IntroduceCoverRequest introduceCoverRequest, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_INTRO_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(introduceCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void 프리랜서_프로필_소개정보_저장_요청결과_검증(FreelancerProfile freelancerProfile, IntroduceCoverRequest introduceCoverRequest) {
        FreelancerProfile updatedFreelancerProfile = freelancerProfileRepository.findById(freelancerProfile.getNum()).get();
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceName()).isEqualTo(introduceCoverRequest.getIntroName());
        Assertions.assertThat(updatedFreelancerProfile.getIntroBackGround()).isEqualTo(introduceCoverRequest.getIntroBackGround());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceContent()).isEqualTo(introduceCoverRequest.getIntroContent());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceVideoURL()).isEqualTo(introduceCoverRequest.getIntroVideoUrl());
    }

    private void 프리랜서_프로필_학력사항_저장_요청(FreelancerProfile freelancerProfile, AcademicAbilityCoverRequests academicAbilityCoverRequests, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_ACADEMIC_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(academicAbilityCoverRequests)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void 프리랜서_프로필_학력사항_저장_요청결과_검증(AcademicAbilityCoverRequest academicAbilityCoverRequest) {
        AcademicAbility academicAbility = academicRepository.findAll().get(0);
        Assertions.assertThat(academicAbility.getSchoolName()).isEqualTo(academicAbilityCoverRequest.getSchoolName());
        Assertions.assertThat(academicAbility.getSchoolLevel()).isEqualTo(academicAbilityCoverRequest.getSchoolLevel());
        Assertions.assertThat(academicAbility.getEnterSchoolDate()).isEqualTo(academicAbilityCoverRequest.getEnterSchoolDate());
        Assertions.assertThat(academicAbility.getGraduationDate()).isEqualTo(academicAbilityCoverRequest.getGraduationDate());
        Assertions.assertThat(academicAbility.getAcademicState()).isEqualTo(academicAbilityCoverRequest.getAcademicState());
        Assertions.assertThat(academicAbility.getMajorName()).isEqualTo(academicAbilityCoverRequest.getMajorName());
    }

    private void 프리랜서_프로필_경력사항_저장_요청(FreelancerProfile freelancerProfile, CareerCoverRequests careerCoverRequests, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_CAREER_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(careerCoverRequests)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void 프리랜서_프로필_경력사항_저장_요청결과_검증(CareerCoverRequest careerCoverRequest) {
        Career career = careerRepository.findAll().get(0);
        Assertions.assertThat(career.getCompanyName()).isEqualTo(careerCoverRequest.getCompanyName());
        Assertions.assertThat(career.getDepartmentName()).isEqualTo(careerCoverRequest.getDepartmentName());
        Assertions.assertThat(career.getCompanyPosition()).isEqualTo(careerCoverRequest.getCompanyPosition());
        Assertions.assertThat(career.getCareerStartDate()).isEqualTo(careerCoverRequest.getCareerStartDate());
        Assertions.assertThat(career.getCareerEndDate()).isEqualTo(careerCoverRequest.getCareerEndDate());
    }

    private void 프리랜서_프로필_프로젝트이력_저장_요청(FreelancerProfile freelancerProfile, ProjectHistoryCoverRequest projectHistoryCoverRequest, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_PROJECT_HISTORY_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(projectHistoryCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void 프리랜서_프로필_프로젝트이력_저장_요청결과_검증(ProjectHistoryCoverRequest projectHistoryCoverRequest) {
        List<ProjectHistory> projectHistories = projectHistoryRepository.findAll();
        Assertions.assertThat(projectHistories).hasSize(1);
        Assertions.assertThat(projectHistories.get(0).getProjectTitle()).isEqualTo(projectHistoryCoverRequest.getProjectTitle());
        Assertions.assertThat(projectHistories.get(0).getProjectStartDate()).isEqualTo(projectHistoryCoverRequest.getProjectStartDate());
        Assertions.assertThat(projectHistories.get(0).getProjectEndDate()).isEqualTo(projectHistoryCoverRequest.getProjectEndDate());
        Assertions.assertThat(projectHistories.get(0).getClientCompany()).isEqualTo(projectHistoryCoverRequest.getClientCompany());
        Assertions.assertThat(projectHistories.get(0).getWorkCompany()).isEqualTo(projectHistoryCoverRequest.getWorkCompany());
        Assertions.assertThat(projectHistories.get(0).getDevelopField()).isEqualTo(projectHistoryCoverRequest.getDevelopField());
        Assertions.assertThat(projectHistories.get(0).getDevelopRole()).isEqualTo(projectHistoryCoverRequest.getDevelopRole());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentModel()).isEqualTo(projectHistoryCoverRequest.getDevelopEnvironmentModel());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentOS()).isEqualTo(projectHistoryCoverRequest.getDevelopEnvironmentOS());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentLanguage()).isEqualTo(projectHistoryCoverRequest.getDevelopEnvironmentLanguage());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentDBName()).isEqualTo(projectHistoryCoverRequest.getDevelopEnvironmentDBName());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentTool()).isEqualTo(projectHistoryCoverRequest.getDevelopEnvironmentTool());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentCommunication()).isEqualTo(projectHistoryCoverRequest.getDevelopEnvironmentCommunication());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentEtc()).isEqualTo(projectHistoryCoverRequest.getDevelopEnvironmentEtc());
        Assertions.assertThat(projectHistories.get(0).getResponsibilityTask()).isEqualTo(projectHistoryCoverRequest.getResponsibilityTask());
    }

    private void 프리랜서_프로필_교육및자격사항_저장_요청(FreelancerProfile freelancerProfile, EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_EDU_AND_LICENSE_AND_LANG_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(educationAndLicenseAndLanguageRequests)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void 프리랜서_프로필_교육및자격사항_저장_요청결과_검증(EducationCoverRequest educationCoverRequest, LicenseCoverRequest licenseCoverRequest, LanguageCoverRequest languageCoverRequest) {
        List<Education> educations = educationRepository.findAll();
        Assertions.assertThat(educations).hasSize(1);
        Assertions.assertThat(educations.get(0).getEducationTitle()).isEqualTo(educationCoverRequest.getEducationTitle());
        Assertions.assertThat(educations.get(0).getEducationOrganization()).isEqualTo(educationCoverRequest.getEducationOrganization());
        Assertions.assertThat(educations.get(0).getEducationStartDate()).isEqualTo(educationCoverRequest.getEducationStartDate());
        Assertions.assertThat(educations.get(0).getEducationEndDate()).isEqualTo(educationCoverRequest.getEducationEndDate());

        List<License> licenses = licenseRepository.findAll();
        Assertions.assertThat(licenses).hasSize(1);
        Assertions.assertThat(licenses.get(0).getLicenseTitle()).isEqualTo(licenseCoverRequest.getLicenseTitle());
        Assertions.assertThat(licenses.get(0).getLicenseIssuer()).isEqualTo(licenseCoverRequest.getLicenseIssuer());
        Assertions.assertThat(licenses.get(0).getAcquisitionDate()).isEqualTo(licenseCoverRequest.getAcquisitionDate());

        List<Language> languages = languageRepository.findAll();
        Assertions.assertThat(languages).hasSize(1);
        Assertions.assertThat(languages.get(0).getLanguageName()).isEqualTo(languageCoverRequest.getLanguageName());
        Assertions.assertThat(languages.get(0).getLanguageAbility()).isEqualTo(languageCoverRequest.getLanguageAbility());
    }

    @AfterEach
    void tearDown() {
        databaseClean.clean();
    }
}
