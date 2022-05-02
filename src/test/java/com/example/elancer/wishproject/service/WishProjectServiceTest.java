package com.example.elancer.wishproject.service;

import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.domain.MemberType;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectBackGround;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.wishproject.dto.WishProjectSaveRequest;
import com.example.elancer.wishproject.model.WishProject;
import com.example.elancer.wishproject.repository.WishProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class WishProjectServiceTest extends ServiceBaseTest {
    @Autowired
    private WishProjectService wishProjectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WishProjectRepository wishProjectRepository;


    @DisplayName("프로젝트찜을 생성한다.")
    @Test
    public void 프로젝트찜_생성() {
        //given
        String memberId = "memberId";
        Freelancer freelancer = freelancerRepository.save(Freelancer.createFreelancer(
                memberId,
                "pwd",
                "name",
                "phone",
                "email",
                "website",
                new Address(CountryType.KR, "zipcode","address1", "address2"),
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        ));

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        MemberDetails memberDetails = new MemberDetails(freelancer.getNum(), freelancer.getUserId(), freelancer.getRole());

        Project project = projectRepository.save(new Project(ProjectBackGround.BLACK));

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
        Freelancer freelancer = freelancerRepository.save(Freelancer.createFreelancer(
                memberId,
                "pwd",
                "name",
                "phone",
                "email",
                "website",
                new Address(CountryType.KR, "zipcode","address1", "address2"),
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        ));

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        MemberDetails memberDetails = new MemberDetails(freelancer.getNum(), freelancer.getUserId(), freelancer.getRole());

        Project project = projectRepository.save(new Project(ProjectBackGround.BLACK));

        WishProject wishProject = wishProjectRepository.save(WishProject.createWishProject(freelancer, project));

        //when
        wishProjectService.deleteWishProject(memberDetails, wishProject.getNum());

        //then
        List<WishProject> wishProjects = wishProjectRepository.findAll();
        Assertions.assertThat(wishProjects).hasSize(0);
    }

    @AfterEach
    void tearDown() {
        databaseClean.clean();
    }
}