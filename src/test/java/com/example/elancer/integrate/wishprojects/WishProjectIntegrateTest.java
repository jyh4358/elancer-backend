package com.example.elancer.integrate.wishprojects;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.integrate.freelancer.LoginHelper;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.member.repository.MemberRepository;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectBackGround;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.token.service.JwtTokenService;
import com.example.elancer.wishprojects.controller.WishProjectControllerPath;
import com.example.elancer.wishprojects.dto.WishProjectDeleteRequest;
import com.example.elancer.wishprojects.dto.WishProjectSaveRequest;
import com.example.elancer.wishprojects.model.WishProject;
import com.example.elancer.wishprojects.repository.WishProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenService jwtTokenService;


    @DisplayName("프로젝트찜 생성 통합테스트")
    @Test
    public void 프로젝트찜_생성() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.ETC));

        Project project = projectRepository.save(new Project(ProjectBackGround.BLACK));

        WishProjectSaveRequest wishProjectSaveRequest = new WishProjectSaveRequest(project.getNum());

        //when
        mockMvc.perform(post(WishProjectControllerPath.WISH_PROJECT_SAVE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(wishProjectSaveRequest)))
                .andExpect(status().isCreated())
                .andDo(print()
                );

        //then
        List<WishProject> wishProjects = wishProjectRepository.findAll();
        Assertions.assertThat(wishProjects).hasSize(1);
    }

    @DisplayName("프로젝트찜 삭제 통합테스트")
    @Test
    public void 프로젝트찜_삭제() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.ETC));

        Project project = projectRepository.save(new Project(ProjectBackGround.BLACK));

        WishProject wishProject = wishProjectRepository.save(WishProject.createWishProject(freelancer, project));

        WishProjectDeleteRequest wishProjectDeleteRequest = new WishProjectDeleteRequest(wishProject.getNum());

        //when
        mockMvc.perform(delete(WishProjectControllerPath.WISH_PROJECT_DELETE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(wishProjectDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(print()
                );

        //then
        List<WishProject> wishProjects = wishProjectRepository.findAll();
        Assertions.assertThat(wishProjects).hasSize(0);
    }
}
