package com.example.elancer.freelancer.service;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
import com.example.elancer.freelancer.dto.FreelancerAccountDetailResponse;
import com.example.elancer.freelancer.dto.response.FreelancerObtainOrdersResponse;
import com.example.elancer.freelancer.model.CareerForm;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerWorkType;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.model.KOSAState;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.PresentWorkState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.model.WorkType;
import com.example.elancer.freelancer.repository.CareerFormRepository;
import com.example.elancer.freelancer.repository.FreelancerWorkTypeRepository;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.project.model.EnterpriseLogo;
import com.example.elancer.project.model.PositionKind;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectBackGround;
import com.example.elancer.project.model.ProjectStatus;
import com.example.elancer.project.model.ProjectStep;
import com.example.elancer.project.model.ProjectType;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.waitproject.repsitory.WaitProjectRepository;
import com.example.elancer.wishprojects.model.WishProject;
import com.example.elancer.wishprojects.repository.WishProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class FreelancerServiceTest extends ServiceBaseTest {
    @Autowired
    private FreelancerService freelancerService;

    @Autowired
    private FreelancerWorkTypeRepository freelancerWorkTypeRepository;

    @Autowired
    private CareerFormRepository careerFormRepository;

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

    @DisplayName("프리랜서 계정 정보가 업데이트 된다")
    @Test
    public void 프리랜서_계정_정보가_업데이트() {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);

        MemberDetails memberDetails = MemberDetails.builder()
                .id(freelancer.getNum())
                .userId(freelancer.getUserId())
                .role(freelancer.getRole())
                .build();

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
                "path",
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

        //when
        freelancerService.coverFreelancerAccountInfo(memberDetails, freelancerAccountCoverRequest);

        //then
        Freelancer updatedFreelancer = freelancerRepository.findById(freelancer.getNum()).get();
        Assertions.assertThat(updatedFreelancer.getName()).isEqualTo(freelancerAccountCoverRequest.getName());
        Assertions.assertThat(updatedFreelancer.getBirthDate()).isEqualTo(freelancerAccountCoverRequest.getBirthDate());
        Assertions.assertThat(updatedFreelancer.getEmail()).isEqualTo(freelancerAccountCoverRequest.getEmail());
        Assertions.assertThat(updatedFreelancer.getPhone()).isEqualTo(freelancerAccountCoverRequest.getPhone());
        Assertions.assertThat(updatedFreelancer.getWebsite()).isEqualTo(freelancerAccountCoverRequest.getWebsite());
        Assertions.assertThat(updatedFreelancer.getAddress().getCountry()).isEqualTo(freelancerAccountCoverRequest.getCountryType());
        Assertions.assertThat(updatedFreelancer.getAddress().getZipcode()).isEqualTo(freelancerAccountCoverRequest.getZipcode());
        Assertions.assertThat(updatedFreelancer.getAddress().getMainAddress()).isEqualTo(freelancerAccountCoverRequest.getMainAddress());
        Assertions.assertThat(updatedFreelancer.getAddress().getDetailAddress()).isEqualTo(freelancerAccountCoverRequest.getDetailAddress());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getWorkEtcField()).isEqualTo(freelancerAccountCoverRequest.getWorkEtcField());
        //TODO 경력기술서 구현후 테스틒 필요.
//        Assertions.assertThat(updatedFreelancer.getCareerForm()).isEqualTo(freelancerAccountCoverRequest.getCareerForm());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getCareerYear()).isEqualTo(freelancerAccountCoverRequest.getCareerYear());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getCareerMonth()).isEqualTo(freelancerAccountCoverRequest.getCareerMonth());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeMonthMinPay()).isEqualTo(freelancerAccountCoverRequest.getHopeMonthMinPay());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeMonthMaxPay()).isEqualTo(freelancerAccountCoverRequest.getHopeMonthMaxPay());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getKosaState()).isEqualTo(freelancerAccountCoverRequest.getKosaState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getMailReceptionState()).isEqualTo(freelancerAccountCoverRequest.getMailReceptionState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getPresentWorkState()).isEqualTo(freelancerAccountCoverRequest.getPresentWorkState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeWorkState()).isEqualTo(freelancerAccountCoverRequest.getHopeWorkState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getWorkPossibleState()).isEqualTo(freelancerAccountCoverRequest.getWorkPossibleState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getWorkStartPossibleDate()).isEqualTo(freelancerAccountCoverRequest.getWorkStartPossibleDate());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeWorkCountry()).isEqualTo(freelancerAccountCoverRequest.getHopeWorkCountry());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeWorkCity()).isEqualTo(freelancerAccountCoverRequest.getHopeWorkCity());

        List<WorkType> workTypes = freelancerWorkTypeRepository.findAll();
        Assertions.assertThat(workTypes).hasSize(2);
        Assertions.assertThat(workTypes.get(0).getFreelancerWorkType()).isEqualTo(freelancerAccountCoverRequest.getFreelancerWorkTypes().get(0));
        Assertions.assertThat(workTypes.get(1).getFreelancerWorkType()).isEqualTo(freelancerAccountCoverRequest.getFreelancerWorkTypes().get(1));

        List<CareerForm> careerForms = careerFormRepository.findAll();
        Assertions.assertThat(careerForms).hasSize(1);
    }

    @DisplayName("프리랜서 계정 정보를 조회한다.")
    @Test
    public void 프리랜서_계정_정보_조회() {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        List<WorkType> workTypes = freelancerWorkTypeRepository.saveAll(Arrays.asList(WorkType.createWorkType(FreelancerWorkType.ACCOUNTING, freelancer), WorkType.createWorkType(FreelancerWorkType.BIGDATA, freelancer)));

        MemberDetails memberDetails = MemberDetails.builder()
                .id(freelancer.getNum())
                .userId(freelancer.getUserId())
                .role(freelancer.getRole())
                .build();

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

        //when
        FreelancerAccountDetailResponse freelancerAccountInfo = freelancerService.findDetailFreelancerAccount(memberDetails);

        //then
        Assertions.assertThat(updatedFreelancer.getName()).isEqualTo(freelancerAccountInfo.getName());
        Assertions.assertThat(freelancerAccountInfo.getThumbnailPath()).isNull();
        Assertions.assertThat(updatedFreelancer.getBirthDate()).isEqualTo(freelancerAccountInfo.getBirthDate());
        Assertions.assertThat(updatedFreelancer.getEmail()).isEqualTo(freelancerAccountInfo.getEmail());
        Assertions.assertThat(updatedFreelancer.getPhone()).isEqualTo(freelancerAccountInfo.getPhone());
        Assertions.assertThat(updatedFreelancer.getWebsite()).isEqualTo(freelancerAccountInfo.getWebsite());
        Assertions.assertThat(updatedFreelancer.getAddress().getCountry()).isEqualTo(freelancerAccountInfo.getCountryType());
        Assertions.assertThat(updatedFreelancer.getAddress().getZipcode()).isEqualTo(freelancerAccountInfo.getZipcode());
        Assertions.assertThat(updatedFreelancer.getAddress().getMainAddress()).isEqualTo(freelancerAccountInfo.getMainAddress());
        Assertions.assertThat(updatedFreelancer.getAddress().getDetailAddress()).isEqualTo(freelancerAccountInfo.getDetailAddress());
        Assertions.assertThat(updatedFreelancer.getCareerFormFilePath()).isNull();
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getWorkEtcField()).isEqualTo(freelancerAccountInfo.getWorkEtcField());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getCareerYear()).isEqualTo(freelancerAccountInfo.getCareerYear());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getCareerMonth()).isEqualTo(freelancerAccountInfo.getCareerMonth());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeMonthMinPay()).isEqualTo(freelancerAccountInfo.getHopeMonthMinPay());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeMonthMaxPay()).isEqualTo(freelancerAccountInfo.getHopeMonthMaxPay());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getKosaState()).isEqualTo(freelancerAccountInfo.getKosaState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getMailReceptionState()).isEqualTo(freelancerAccountInfo.getMailReceptionState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getPresentWorkState()).isEqualTo(freelancerAccountInfo.getPresentWorkState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeWorkState()).isEqualTo(freelancerAccountInfo.getHopeWorkState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getWorkPossibleState()).isEqualTo(freelancerAccountInfo.getWorkPossibleState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getWorkStartPossibleDate()).isEqualTo(freelancerAccountInfo.getWorkStartPossibleDate());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeWorkCountry()).isEqualTo(freelancerAccountInfo.getHopeWorkCountry());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeWorkCity()).isEqualTo(freelancerAccountInfo.getHopeWorkCity());

        Assertions.assertThat(workTypes.get(0).getFreelancerWorkType()).isEqualTo(freelancerAccountInfo.getFreelancerWorkTypes().get(0));
        Assertions.assertThat(workTypes.get(1).getFreelancerWorkType()).isEqualTo(freelancerAccountInfo.getFreelancerWorkTypes().get(1));
    }

    @DisplayName("프리랜서 수주관리 페이지 정보 조회")
    @Test
    public void 프리랜서_수주관리_조회() {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);

        MemberDetails memberDetails = MemberDetails.builder()
                .id(freelancer.getNum())
                .userId(freelancer.getUserId())
                .role(freelancer.getRole())
                .build();

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

        ApplyProject applyProject = applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));

        InterviewProject interviewProject = interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));

        WaitProject waitProject = waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));

        WishProject wishProject = wishProjectRepository.save(WishProject.createWishProject(freelancer, project));

        //when
        FreelancerObtainOrdersResponse freelancerObtainOrders = freelancerService.findFreelancerObtainOrders(memberDetails);

        //then
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectCount()).isEqualTo(1);
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectCount()).isEqualTo(1);
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectCount()).isEqualTo(1);
        Assertions.assertThat(freelancerObtainOrders.getWishProjectCount()).isEqualTo(1);
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses()).hasSize(1);
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getProjectNum()).isEqualTo(project.getNum());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getProjectName()).isEqualTo(project.getProjectName());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getPositionKind()).isEqualTo(project.getPositionKind());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getDemandCareer()).isEqualTo(project.demandCareer());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getHeadCount()).isEqualTo(project.getHeadCount());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getHeadCount()).isEqualTo(project.getHeadCount());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getProjectStateDate()).isEqualTo(project.getProjectStateDate());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getProjectEndDate()).isEqualTo(project.getProjectEndDate());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getMinMoney()).isEqualTo(project.getMinMoney());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getMaxMoney()).isEqualTo(project.getMaxMoney());
        Assertions.assertThat(freelancerObtainOrders.getApplyProjectResponses().get(0).getCreatedDate()).isEqualTo(project.getCreatedDate().toLocalDate());

        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses()).hasSize(1);
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getProjectNum()).isEqualTo(project.getNum());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getProjectName()).isEqualTo(project.getProjectName());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getPositionKind()).isEqualTo(project.getPositionKind());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getDemandCareer()).isEqualTo(project.demandCareer());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getHeadCount()).isEqualTo(project.getHeadCount());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getHeadCount()).isEqualTo(project.getHeadCount());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getProjectStateDate()).isEqualTo(project.getProjectStateDate());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getProjectEndDate()).isEqualTo(project.getProjectEndDate());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getMinMoney()).isEqualTo(project.getMinMoney());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getMaxMoney()).isEqualTo(project.getMaxMoney());
        Assertions.assertThat(freelancerObtainOrders.getInterviewProjectResponses().get(0).getCreatedDate()).isEqualTo(project.getCreatedDate().toLocalDate());

        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses()).hasSize(1);
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getProjectNum()).isEqualTo(project.getNum());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getProjectName()).isEqualTo(project.getProjectName());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getPositionKind()).isEqualTo(project.getPositionKind());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getDemandCareer()).isEqualTo(project.demandCareer());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getHeadCount()).isEqualTo(project.getHeadCount());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getHeadCount()).isEqualTo(project.getHeadCount());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getProjectStateDate()).isEqualTo(project.getProjectStateDate());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getProjectEndDate()).isEqualTo(project.getProjectEndDate());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getMinMoney()).isEqualTo(project.getMinMoney());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getMaxMoney()).isEqualTo(project.getMaxMoney());
        Assertions.assertThat(freelancerObtainOrders.getJoinedProjectResponses().get(0).getCreatedDate()).isEqualTo(project.getCreatedDate().toLocalDate());

        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses()).hasSize(1);
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getProjectNum()).isEqualTo(project.getNum());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getProjectName()).isEqualTo(project.getProjectName());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getPositionKind()).isEqualTo(project.getPositionKind());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getDemandCareer()).isEqualTo(project.demandCareer());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getHeadCount()).isEqualTo(project.getHeadCount());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getHeadCount()).isEqualTo(project.getHeadCount());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getProjectStateDate()).isEqualTo(project.getProjectStateDate());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getProjectEndDate()).isEqualTo(project.getProjectEndDate());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getMinMoney()).isEqualTo(project.getMinMoney());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getMaxMoney()).isEqualTo(project.getMaxMoney());
        Assertions.assertThat(freelancerObtainOrders.getWishProjectResponses().get(0).getCreatedDate()).isEqualTo(project.getCreatedDate().toLocalDate());
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}