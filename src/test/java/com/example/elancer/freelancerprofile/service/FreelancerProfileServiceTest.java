package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.CareerCoverRequest;
import com.example.elancer.freelancerprofile.dto.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.IntroduceCoverRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.dto.ProjectHistoryCoverRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.academic.AcademicRepository;
import com.example.elancer.freelancerprofile.repository.career.CareerRepository;
import com.example.elancer.freelancerprofile.repository.projecthistory.ProjectHistoryRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.MemberType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("h2")
@SpringBootTest
class FreelancerProfileServiceTest {

    @Autowired
    private FreelancerProfileService freelancerProfileService;

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


    @DisplayName("프리랜서 소개정보가 저장된다")
    @Test
    public void 프리랜서_소개정보_저장() {
        //given
        String memberId = "memberId";
        Freelancer freelancer = freelancerRepository.save(Freelancer.createFreelancer(
                memberId,
                "pwd",
                "name",
                "phone",
                "email",
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        ));

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        IntroduceCoverRequest introduceCoverRequest =
                new IntroduceCoverRequest("testname", IntroBackGround.COBALT_BLUE, "url", "introContent");
        MemberDetails memberDetails = new MemberDetails(memberId);

        //when
        freelancerProfileService.coverFreelancerIntroduce(memberDetails, freelancer.getNum(), introduceCoverRequest);

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
        Freelancer freelancer = freelancerRepository.save(Freelancer.createFreelancer(
                memberId,
                "pwd",
                "name",
                "phone",
                "email",
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        ));

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        AcademicAbilityCoverRequest academicAbilityCoverRequest = new AcademicAbilityCoverRequest(
                "schoolName",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2016, 02, 01),
                LocalDate.of(2021, 02, 01),
                AcademicState.GRADUATION,
                "전자공학"
        );
        MemberDetails memberDetails = new MemberDetails(memberId);

        //when
        freelancerProfileService.coverFreelancerAcademicAbility(memberDetails, freelancerProfile.getNum(), new AcademicAbilityCoverRequests(Arrays.asList(academicAbilityCoverRequest)));

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
        Freelancer freelancer = freelancerRepository.save(Freelancer.createFreelancer(
                memberId,
                "pwd",
                "name",
                "phone",
                "email",
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        ));

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        CareerCoverRequest careerCoverRequest = new CareerCoverRequest(
                "companyName",
                "departmentName",
                CompanyPosition.ASSISTANT_MANAGER,
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2022, 02, 01)
        );


        MemberDetails memberDetails = new MemberDetails(memberId);

        //when
        freelancerProfileService.coverFreelancerCareer(memberDetails, freelancerProfile.getNum(), new CareerCoverRequests(Arrays.asList(careerCoverRequest)));

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
        Freelancer freelancer = freelancerRepository.save(Freelancer.createFreelancer(
                memberId,
                "pwd",
                "name",
                "phone",
                "email",
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        ));

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

        MemberDetails memberDetails = new MemberDetails(memberId);

        //when
        freelancerProfileService.coverFreelancerProjectHistory(memberDetails, freelancerProfile.getNum(), projectHistoryCoverRequest);

        //then
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


}