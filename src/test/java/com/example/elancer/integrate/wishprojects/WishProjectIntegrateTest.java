package com.example.elancer.integrate.wishprojects;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.annotation.WithMockCustomUser;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.member.domain.Member;
import com.example.elancer.member.repository.MemberRepository;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectBackGround;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.wishprojects.controller.WishProjectControllerPath;
import com.example.elancer.wishprojects.dto.WishProjectSaveRequest;
import com.example.elancer.wishprojects.model.WishProject;
import com.example.elancer.wishprojects.repository.WishProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WishProjectIntegrateTest extends IntegrateBaseTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WishProjectRepository wishProjectRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Freelancer freelancer;

    @DisplayName("프로젝트찜 생성 통합테스트")
    @WithUserDetails(value = "memberId")
//    @Test
    public void 프로젝트찜_생성() throws Exception {
        //given
//        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository);
        Member member = memberRepository.findByUserId(freelancer.getUserId()).orElseThrow(() -> new UsernameNotFoundException("check id"));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        Project project = projectRepository.save(new Project(ProjectBackGround.BLACK));

        WishProjectSaveRequest wishProjectSaveRequest = new WishProjectSaveRequest(project.getNum());

        //when
        mockMvc.perform(post(WishProjectControllerPath.WISH_PROJECT_SAVE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(wishProjectSaveRequest)))
                .andExpect(status().isCreated())
                .andDo(print()
        );

        //then
        List<WishProject> wishProjects = wishProjectRepository.findAll();
        Assertions.assertThat(wishProjects).hasSize(1);
    }
}
