package com.example.elancer.document.interviewproject;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.model.*;
import com.example.elancer.project.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class InterviewProjectDocumentTest extends DocumentBaseTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ApplyProjectRepository applyProjectRepository;


    @DisplayName("프로젝트 인터뷰 요청 문서화")
//    @Test
    public void 프로젝트_인터뷰_요청() throws Exception {
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);
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

//        ApplyProject applyProject = applyProjectRepository.save(new ApplyProject(freelancer, project));


//        new CreateInterviewProjectRequest(applyproject)
    }
}
