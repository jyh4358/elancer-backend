package com.example.elancer.wishprojects.service;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.project.model.*;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.wishprojects.dto.WishProjectDeleteRequest;
import com.example.elancer.wishprojects.dto.WishProjectSaveRequest;
import com.example.elancer.wishprojects.model.WishProject;
import com.example.elancer.wishprojects.repository.WishProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;


class WishProjectServiceTest extends ServiceBaseTest {
    @Autowired
    private WishProjectService wishProjectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WishProjectRepository wishProjectRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;


    @DisplayName("프로젝트찜을 생성한다.")
    @Test
    public void 프로젝트찜_생성() {
        //given
        String memberId = "memberId";
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.PLANNER));

        MemberDetails memberDetails = new MemberDetails(freelancer.getNum(), freelancer.getUserId(), freelancer.getRole());

        Project project = projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
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
        WishProjectSaveRequest wishProjectSaveRequest = new WishProjectSaveRequest(project.getNum());

        //when
        wishProjectService.saveWishProject(memberDetails, wishProjectSaveRequest);

        //then
        List<WishProject> wishProjects = wishProjectRepository.findAll();
        Assertions.assertThat(wishProjects).hasSize(1);
    }

    @DisplayName("프로젝트찜을 삭제한다.")
    @Test
    public void 프로젝트찜_삭제() {
        //given
        String memberId = "memberId";
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.CROWD_WORKER));

        MemberDetails memberDetails = new MemberDetails(freelancer.getNum(), freelancer.getUserId(), freelancer.getRole());

        Project project = projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
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
        WishProject wishProject = wishProjectRepository.save(WishProject.createWishProject(freelancer, project));

        WishProjectDeleteRequest wishProjectDeleteRequest = new WishProjectDeleteRequest(wishProject.getNum());
        //when
        wishProjectService.deleteWishProject(memberDetails, wishProjectDeleteRequest);

        //then
        List<WishProject> wishProjects = wishProjectRepository.findAll();
        Assertions.assertThat(wishProjects).hasSize(0);
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}