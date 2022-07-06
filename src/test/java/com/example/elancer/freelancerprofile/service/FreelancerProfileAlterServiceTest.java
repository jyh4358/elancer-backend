package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationAndLicenseAndLanguageRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.IntroduceCoverRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.dto.request.LanguageCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.LicenseCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.ProjectHistoryCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.ProjectHistoryRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import com.example.elancer.freelancerprofile.model.education.Education;
import com.example.elancer.freelancerprofile.model.language.Language;
import com.example.elancer.freelancerprofile.model.language.LanguageAbility;
import com.example.elancer.freelancerprofile.model.license.License;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.example.elancer.freelancerprofile.repository.profile.academic.AcademicRepository;
import com.example.elancer.freelancerprofile.repository.profile.career.CareerRepository;
import com.example.elancer.freelancerprofile.repository.profile.education.EducationRepository;
import com.example.elancer.freelancerprofile.repository.profile.language.LanguageRepository;
import com.example.elancer.freelancerprofile.repository.profile.license.LicenseRepository;
import com.example.elancer.freelancerprofile.repository.profile.projecthistory.ProjectHistoryRepository;
import com.example.elancer.freelancerprofile.service.profile.FreelancerProfileAlterService;
import com.example.elancer.login.auth.dto.MemberDetails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class FreelancerProfileAlterServiceTest extends ServiceBaseTest {

    @Autowired
    private FreelancerProfileAlterService freelancerProfileAlterService;

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


    @DisplayName("프리랜서 소개정보가 저장된다")
    @Test
    public void 프리랜서_소개정보_저장() {
        //given
        String memberId = "memberId";
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        IntroduceCoverRequest introduceCoverRequest =
                new IntroduceCoverRequest("greeting","testname", IntroBackGround.COBALT_BLUE, "url", "introContent");

        MemberDetails memberDetails = MemberDetails.builder()
                .id(freelancer.getNum())
                .userId(freelancer.getUserId())
                .role(freelancer.getRole())
                .build();

        //when
        freelancerProfileAlterService.coverFreelancerIntroduce(memberDetails, introduceCoverRequest);

        //then
        FreelancerProfile updatedFreelancerProfile = freelancerProfileRepository.findById(freelancer.getNum()).get();
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceName()).isEqualTo(introduceCoverRequest.getIntroName());
        Assertions.assertThat(updatedFreelancerProfile.getIntroBackGround()).isEqualTo(introduceCoverRequest.getIntroBackGround());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceVideoURL()).isEqualTo(introduceCoverRequest.getIntroVideoUrl());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceContent()).isEqualTo(introduceCoverRequest.getIntroContent());
    }

    @DisplayName("프리랜서 학력정보가 저장된다")
    @Test
    public void 프리랜서_학력정보_저장() {
        //given
        String memberId = "memberId";

        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        AcademicAbilityCoverRequest academicAbilityCoverRequest = new AcademicAbilityCoverRequest(
                "schoolName",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2016, 02, 01),
                LocalDate.of(2021, 02, 01),
                AcademicState.GRADUATION,
                "전자공학"
        );
        MemberDetails memberDetails = MemberDetails.builder()
                .id(freelancer.getNum())
                .userId(freelancer.getUserId())
                .role(freelancer.getRole())
                .build();

        //when
        freelancerProfileAlterService.coverFreelancerAcademicAbility(memberDetails, new AcademicAbilityCoverRequests(Arrays.asList(academicAbilityCoverRequest)));

        //then
        List<AcademicAbility> academicAbilityList = academicRepository.findAll();
        Assertions.assertThat(academicAbilityList).hasSize(1);
        Assertions.assertThat(academicAbilityList.get(0).getSchoolName()).isEqualTo(academicAbilityCoverRequest.getSchoolName());
        Assertions.assertThat(academicAbilityList.get(0).getSchoolLevel()).isEqualTo(academicAbilityCoverRequest.getSchoolLevel());
        Assertions.assertThat(academicAbilityList.get(0).getEnterSchoolDate()).isEqualTo(academicAbilityCoverRequest.getEnterSchoolDate());
        Assertions.assertThat(academicAbilityList.get(0).getGraduationDate()).isEqualTo(academicAbilityCoverRequest.getGraduationDate());
        Assertions.assertThat(academicAbilityList.get(0).getAcademicState()).isEqualTo(academicAbilityCoverRequest.getAcademicState());
        Assertions.assertThat(academicAbilityList.get(0).getMajorName()).isEqualTo(academicAbilityCoverRequest.getMajorName());
    }

    @DisplayName("프리랜서 근무경력 정보가 저장된다")
    @Test
    public void 프리랜서_근무경력_저장() {
        //given
        String memberId = "memberId";
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        CareerCoverRequest careerCoverRequest = new CareerCoverRequest(
                "companyName",
                "departmentName",
                CompanyPosition.ASSISTANT_MANAGER,
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2022, 02, 01)
        );


        MemberDetails memberDetails = MemberDetails.builder()
                .id(freelancer.getNum())
                .userId(freelancer.getUserId())
                .role(freelancer.getRole())
                .build();

        //when
        freelancerProfileAlterService.coverFreelancerCareer(memberDetails, new CareerCoverRequests(Arrays.asList(careerCoverRequest)));

        //then
        List<Career> careers = careerRepository.findAll();
        Assertions.assertThat(careers).hasSize(1);
        Assertions.assertThat(careers.get(0).getCompanyName()).isEqualTo(careerCoverRequest.getCompanyName());
        Assertions.assertThat(careers.get(0).getDepartmentName()).isEqualTo(careerCoverRequest.getDepartmentName());
        Assertions.assertThat(careers.get(0).getCompanyPosition()).isEqualTo(careerCoverRequest.getCompanyPosition());
        Assertions.assertThat(careers.get(0).getCareerStartDate()).isEqualTo(careerCoverRequest.getCareerStartDate());
        Assertions.assertThat(careers.get(0).getCareerEndDate()).isEqualTo(careerCoverRequest.getCareerEndDate());
    }

    @DisplayName("프리랜서 프로젝트 수행이력 정보가 저장된다")
    @Test
    public void 프리랜서_프로젝트_수행이력_저장() {
        //given
        String memberId = "memberId";
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        ProjectHistoryRequest projectHistoryRequest = new ProjectHistoryRequest(
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

        ProjectHistoryCoverRequests projectHistoryCoverRequest = new ProjectHistoryCoverRequests(Arrays.asList(projectHistoryRequest));

        MemberDetails memberDetails = MemberDetails.builder()
                .id(freelancer.getNum())
                .userId(freelancer.getUserId())
                .role(freelancer.getRole())
                .build();

        //when
        freelancerProfileAlterService.coverFreelancerProjectHistory(memberDetails, projectHistoryCoverRequest);

        //then
        List<ProjectHistory> projectHistories = projectHistoryRepository.findAll();
        Assertions.assertThat(projectHistories).hasSize(1);
        Assertions.assertThat(projectHistories.get(0).getProjectTitle()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getProjectTitle());
        Assertions.assertThat(projectHistories.get(0).getProjectStartDate()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getProjectStartDate());
        Assertions.assertThat(projectHistories.get(0).getProjectEndDate()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getProjectEndDate());
        Assertions.assertThat(projectHistories.get(0).getClientCompany()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getClientCompany());
        Assertions.assertThat(projectHistories.get(0).getWorkCompany()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getWorkCompany());
        Assertions.assertThat(projectHistories.get(0).getDevelopField()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getDevelopField());
        Assertions.assertThat(projectHistories.get(0).getDevelopRole()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getDevelopRole());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentModel()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getDevelopEnvironmentModel());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentOS()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getDevelopEnvironmentOS());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentLanguage()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getDevelopEnvironmentLanguage());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentDBName()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getDevelopEnvironmentDBName());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentTool()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getDevelopEnvironmentTool());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentCommunication()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getDevelopEnvironmentCommunication());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentEtc()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getDevelopEnvironmentEtc());
        Assertions.assertThat(projectHistories.get(0).getResponsibilityTask()).isEqualTo(projectHistoryCoverRequest.getProjectHistoryRequestList().get(0).getResponsibilityTask());
    }

    @DisplayName("프리랜서 프로필 교육 및 자격사항 저장 테스트")
    @Test
    public void 프리랜서_프로필_교육_및_자격사항_저장() {
        //given
        String memberId = "memberId";
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        EducationCoverRequest educationCoverRequest = new EducationCoverRequest("eduTitle", "eduOrganization", LocalDate.of(2020, 10, 01), LocalDate.of(2021, 01, 01));
        LicenseCoverRequest licenseCoverRequest = new LicenseCoverRequest("licenseTitle", "issuer", LocalDate.of(2020, 05, 20));
        LanguageCoverRequest languageCoverRequest = new LanguageCoverRequest("lanuageName", LanguageAbility.MIDDLE);

        EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests = new EducationAndLicenseAndLanguageRequests(
                Arrays.asList(educationCoverRequest), Arrays.asList(licenseCoverRequest), Arrays.asList(languageCoverRequest));

        MemberDetails memberDetails = MemberDetails.builder()
                .id(freelancer.getNum())
                .userId(freelancer.getUserId())
                .role(freelancer.getRole())
                .build();

        //when
        freelancerProfileAlterService.coverFreelancerEducationAndLicenseAndLanguage(memberDetails, educationAndLicenseAndLanguageRequests);

        //then
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
        databaseCleaner.clean();
    }
}