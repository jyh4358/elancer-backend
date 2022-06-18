package com.example.elancer.interviewproject.service;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.model.InterviewSatus;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.project.model.*;
import com.example.elancer.project.repository.ProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

class InterviewProjectServiceTest extends ServiceBaseTest {

    @Autowired
    private ApplyProjectRepository applyProjectRepository;

    @Autowired
    private InterviewProjectService interviewProjectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private InterviewProjectRepository interviewProjectRepository;

    @DisplayName("프로젝트 인터뷰 요청")
//    @Test
    public void 프로젝트_인터뷰_요청() {

        // given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
//        Project project = projectRepository.save(new Project(
//                ProjectType.TELEWORKING,
//                ProjectBackGround.BLACK,
//                EnterpriseLogo.COUPANG,
//                ProjectStep.ANALYSIS,
//                "쇼핑몰",
//                PositionKind.DEVELOPER,
//                "Java",
//                "쇼핑몰 프로젝트",
//                5,
//                5,
//                "1.프로젝트 명 .....",
//                LocalDate.now(),
//                LocalDate.now().plusMonths(1L),
//                LocalDate.now().plusDays(10L),
//                new Address(CountryType.KR, "123-123", "메인 주소", "상세 주소"),
//                6000000,
//                10000000,
//                5,
//                3,
//                30,
//                35,
//                ProjectStatus.PROGRESS,
//                enterprise
//        ));
//        applyProjectRepository.save(new ApplyProject(freelancer, project));

//        CreateInterviewProjectRequest createInterviewProjectRequest = new CreateInterviewProjectRequest(
//                project.getNum(), freelancer.getNum()
//        );
        MemberDetails memberDetails = MemberDetails.userDetailsFrom(enterprise);

        // when
//        interviewProjectService.createInterviewProject(createInterviewProjectRequest, memberDetails);

        //then
//        InterviewProject interviewProject = interviewProjectRepository.findAll().get(0);
//        Assertions.assertThat(interviewProject.getProject().getNum()).isEqualTo(project.getNum());
//        Assertions.assertThat(interviewProject.getFreelancer().getNum()).isEqualTo(freelancer.getNum());
//        Assertions.assertThat(interviewProject.getInterviewSatus()).isEqualTo(InterviewSatus.WAITING);

    }

    // todo - 나머지 테스트도 구현
}