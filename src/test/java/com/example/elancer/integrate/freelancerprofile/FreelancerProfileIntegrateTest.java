package com.example.elancer.integrate.freelancerprofile;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.controller.FreelancerProfileAlterControllerPath;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.CareerCoverRequest;
import com.example.elancer.freelancerprofile.dto.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.EducationAndLicenseAndLanguageRequests;
import com.example.elancer.freelancerprofile.dto.EducationCoverRequest;
import com.example.elancer.freelancerprofile.dto.EducationAndLicenseAndLanguageRequests;
import com.example.elancer.freelancerprofile.dto.EducationCoverRequest;
import com.example.elancer.freelancerprofile.dto.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.dto.LanguageCoverRequest;
import com.example.elancer.freelancerprofile.dto.LicenseCoverRequest;
import com.example.elancer.freelancerprofile.dto.ProjectHistoryCoverRequest;
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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        IntroduceCoverRequest introduceCoverRequest = new IntroduceCoverRequest("introName", IntroBackGround.COBALT_BLUE, "introVideoUrl", "introContent");

        //when
        프리랜서_프로필_소개정보_저장_요청(freelancerProfile, introduceCoverRequest);

        //then
        프리랜서_프로필_소개정보_저장_요청결과_검증(freelancerProfile, introduceCoverRequest);
    }

    @DisplayName("프리랜서 프로필 학력 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_학력정보_저장() throws Exception {
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

        //when
        프리랜서_프로필_학력사항_저장_요청(freelancerProfile, academicAbilityCoverRequests);

        //then
        프리랜서_프로필_학력사항_저장_요청결과_검증(academicAbilityCoverRequest);
    }

    @DisplayName("프리랜서 프로필 경력 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_경력정보_저장() throws Exception {
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

        //when
        프리랜서_프로필_경력사항_저장_요청(freelancerProfile, careerCoverRequests);

        //then
        프리랜서_프로필_경력사항_저장_요청결과_검증(careerCoverRequests.getCareerCoverRequests().get(0));
    }

    @DisplayName("프리랜서 프로필 프로젝트이력 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_프로젝트이력_저장() throws Exception {
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

        //when
        프리랜서_프로필_프로젝트이력_저장_요청(freelancerProfile, projectHistoryCoverRequest);

        //then
        프리랜서_프로필_프로젝트이력_저장_요청결과_검증(projectHistoryCoverRequest);
    }

    @DisplayName("프리랜서 프로필 교육 및 자격사항 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_교육및자격사항_저장() throws Exception {
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

        //when
        프리랜서_프로필_교육및자격사항_저장_요청(freelancerProfile, educationAndLicenseAndLanguageRequests);

        //then
        프리랜서_프로필_교육및자격사항_저장_요청결과_검증(educationCoverRequest, licenseCoverRequest, languageCoverRequest);
    }

    private void 프리랜서_프로필_소개정보_저장_요청(FreelancerProfile freelancerProfile, IntroduceCoverRequest introduceCoverRequest) throws Exception {
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_INTRO_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    private void 프리랜서_프로필_학력사항_저장_요청(FreelancerProfile freelancerProfile, AcademicAbilityCoverRequests academicAbilityCoverRequests) throws Exception {
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_ACADEMIC_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    private void 프리랜서_프로필_경력사항_저장_요청(FreelancerProfile freelancerProfile, CareerCoverRequests careerCoverRequests) throws Exception {
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_CAREER_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    private void 프리랜서_프로필_프로젝트이력_저장_요청(FreelancerProfile freelancerProfile, ProjectHistoryCoverRequest projectHistoryCoverRequest) throws Exception {
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_PROJECT_HISTORY_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    private void 프리랜서_프로필_교육및자격사항_저장_요청(FreelancerProfile freelancerProfile, EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests) throws Exception {
        String path = FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_EDU_AND_LICENSE_AND_LANG_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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
}
