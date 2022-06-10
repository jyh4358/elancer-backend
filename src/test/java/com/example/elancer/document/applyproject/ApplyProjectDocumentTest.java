package com.example.elancer.document.applyproject;

import com.example.elancer.applyproject.controller.ApplyProjectControllerPath;
import com.example.elancer.applyproject.dto.ApplyProjectCreateRequest;
import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.model.EnterpriseLogo;
import com.example.elancer.project.model.PositionKind;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectBackGround;
import com.example.elancer.project.model.ProjectStep;
import com.example.elancer.project.model.ProjectType;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApplyProjectDocumentTest extends DocumentBaseTest {

    @Autowired
    private ProjectRepository projectRepository;


    @DisplayName("프로젝트 지원 문서화")
    @Test
    public void 프로젝트_지원_생성() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);
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
                35
        ));

        ApplyProjectCreateRequest applyProjectCreateRequest = new ApplyProjectCreateRequest(project.getNum());

        //when & then
        mockMvc.perform(post(ApplyProjectControllerPath.CREATE_APPLY_PROJECT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(applyProjectCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("apply-project-create",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        requestFields(
                                fieldWithPath("projectNum").type("Long").description("프로젝트 식별자 정보 필드.")
                        )
                        ));

    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}
