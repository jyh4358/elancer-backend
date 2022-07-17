package com.example.elancer.document.freelancer;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.controller.position.FreelancerPositionControllerPath;
import com.example.elancer.freelancerprofile.dto.request.position.DesignerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PlannerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PositionEtcCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PublisherCoverRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspDetailSkill;
import com.example.elancer.freelancerprofile.model.position.etc.EtcDetailRole;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerDetailField;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingDetailSkill;
import com.example.elancer.freelancerprofile.repository.profile.academic.AcademicRepository;
import com.example.elancer.freelancerprofile.repository.profile.career.CareerRepository;
import com.example.elancer.freelancerprofile.repository.profile.education.EducationRepository;
import com.example.elancer.freelancerprofile.repository.profile.language.LanguageRepository;
import com.example.elancer.freelancerprofile.repository.profile.license.LicenseRepository;
import com.example.elancer.freelancerprofile.repository.profile.projecthistory.ProjectHistoryRepository;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerPositionDocumentTest extends DocumentBaseTest {

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

    @DisplayName("프리랜서 프로필 개발자 포지션 저장 문서화")
    @Test
    public void 프리랜서_프로필_개발자_포지션_저장_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        DeveloperCoverRequest developerCoverRequest = new DeveloperCoverRequest(
                Arrays.asList("Java", "Spring"),
                Arrays.asList("백엔드 개발자"),
                Arrays.asList(JavaDetailSkill.SPRING, JavaDetailSkill.BACKEND),
                Arrays.asList(MobileAppDetailSkill.ANDROID),
                Arrays.asList(PhpOrAspDetailSkill.PHP),
                Arrays.asList(DotNetDetailSkill.C),
                Arrays.asList(JavaScriptDetailSkill.ANGULAR),
                Arrays.asList(CDetailSkill.EMBEDDED),
                Arrays.asList(DBDetailSkill.MARIADB, DBDetailSkill.MYSQL),
                "etc"
        );

        //when & then
        String path = FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_DEVELOPER_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(developerCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("freelancer-profile-developer-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("focusSkills").type("List<String>").description("프리랜서 개발자 주언어 정보 필드."),
                                fieldWithPath("roles").type("List<String>").description("프리랜서 개발자 역할 정보 필드."),
                                fieldWithPath("javaDetailSkills").type("List<JavaDetailSkill>").description("프리랜서 개발자 JAVA 스킬 정보 필드."),
                                fieldWithPath("mobileAppDetailSkills").type("List<MobileAppDetailSkill>").description("프리랜서 개발자 Mobile App 스킬 정보 필드."),
                                fieldWithPath("phpOrAspDetailSkills").type("List<PhpOrAspDetailSkill>").description("프리랜서 개발자 PHP/ASP 스킬 정보 필드."),
                                fieldWithPath("dotNetDetailSkills").type("List<DotNetDetailSkill>").description("프리랜서 개발자 .NET 스킬 정보 필드."),
                                fieldWithPath("javaScriptDetailSkills").type("List<JavaScriptDetailSkill>").description("프리랜서 개발자 JavaScript 스킬 정보 필드."),
                                fieldWithPath("cDetailSkills").type("List<CDetailSkill>").description("프리랜서 개발자 C/C++ 스킬 정보 필드."),
                                fieldWithPath("dbDetailSkills").type("List<DBDetailSkill>").description("프리랜서 개발자 DB 스킬 정보 필드."),
                                fieldWithPath("etcSkill").type("String").description("프리랜서 개발자 기티스킬 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 퍼블리셔 포지션 저장 문서화")
    @Test
    public void 프리랜서_프로필_퍼블리셔_포지션_저장_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        PublisherCoverRequest publisherCoverRequest
                = new PublisherCoverRequest(Arrays.asList(PublishingDetailSkill.HTML5, PublishingDetailSkill.CSS, PublishingDetailSkill.JQUERY), "etcSkill");

        //when & then
        mockMvc.perform(put(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_PUBLISHER_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(publisherCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("freelancer-profile-publisher-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("publishingDetailSkills").type("List<PublishingDetailSkill>").description("프리랜서 퍼블리셔 스킬,경험 정보 필드."),
                                fieldWithPath("etcSkill").type("String").description("프리랜서 퍼블리셔 기티스킬 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 디자이너 포지션 저장 문서화")
    @Test
    public void 프리랜서_프로필_디자이너_포지션_저장_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        DesignerCoverRequest designerCoverRequest = new DesignerCoverRequest(
                Arrays.asList(DesignDetailRole.APPDESIGN, DesignDetailRole.GAMEDESIGN),
                "etcRole",
                Arrays.asList(DesignDetailSkill.AFEREEFFECT, DesignDetailSkill.THREEDMAXANDMAYA),
                "etcSkill"
        );

        //when & then
        mockMvc.perform(put(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_DESIGNER_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(designerCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("freelancer-profile-designer-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("designDetailRoles").type("List<DesignDetailRole>").description("프리랜서 디자이너 역할 정보 필드."),
                                fieldWithPath("etcRole").type("String").description("프리랜서 디자이너 기티역할 정보 필드."),
                                fieldWithPath("designDetailSkills").type("List<DesignDetailSkill>").description("프리랜서 디자이너 스킬 정보 필드."),
                                fieldWithPath("etcSkill").type("String").description("프리랜서 디자이너 기티스킬 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 기획자 포지션 저장 문서화")
    @Test
    public void 프리랜서_프로필_기획자_포지션_저장_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        PlannerCoverRequest plannerCoverRequest = new PlannerCoverRequest(Arrays.asList(PlannerDetailField.ACCOUNTING, PlannerDetailField.APPPLAN), "etcField");

        //when & then
        mockMvc.perform(put(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_PLANNER_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(plannerCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("freelancer-profile-planner-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("plannerDetailFields").type("List<PlannerDetailField>").description("프리랜서 기획자 업무분야 정보 필드."),
                                fieldWithPath("etcField").type("String").description("프리랜서 기획자 기티분야 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 크라우드워커 포지션 저장 문서화")
    @Test
    public void 프리랜서_프로필_크라우드워커_포지션_저장_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        //when & then
        mockMvc.perform(put(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_CROWD_WORKER_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("freelancer-profile-crowdworker-cover"));
    }

    @DisplayName("프리랜서 프로필 기타 포지션 저장 문서화")
    @Test
    public void 프리랜서_프로필_기타_포지션_저장_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        PositionEtcCoverRequest positionEtcCoverRequest = new PositionEtcCoverRequest(Arrays.asList(EtcDetailRole.AA, EtcDetailRole.DBA), "positionEtcRole");

        //when & then
        mockMvc.perform(put(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_ETC_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(positionEtcCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("freelancer-profile-etc-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt 토큰 인증 헤더 필드.")
                        ),
                        requestFields(
                                fieldWithPath("etcDetailRoles").type("List<EtcDetailRole>").description("프리랜서 기티파트 역할 정보 필드."),
                                fieldWithPath("positionEtcRole").type("String").description("프리랜서 기타파트 기티분야 역할외 정보 필드.")
                        )
                ));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}
