package com.example.elancer.integrate.freelancerprofile;


import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.controller.position.FreelancerPositionFindControllerPath;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailSkill;
import com.example.elancer.freelancerprofile.model.position.designer.DesignRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignSkill;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.ClangSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspSkill;
import com.example.elancer.freelancerprofile.model.position.etc.EtcDetailRole;
import com.example.elancer.freelancerprofile.model.position.etc.EtcRole;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerDetailField;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerField;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingDetailSkill;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingSkill;
import com.example.elancer.freelancerprofile.repository.position.designer.DesignerRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.etc.PositionEtcRepository;
import com.example.elancer.freelancerprofile.repository.position.planner.PlannerRepository;
import com.example.elancer.freelancerprofile.repository.position.publisher.PublisherRepository;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerPositionFindIntegrateTest extends IntegrateBaseTest {

    @Autowired
    private DeveloperRepository developerRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private DesignerRepository designerRepository;
    @Autowired
    private PlannerRepository plannerRepository;
    @Autowired
    private PositionEtcRepository positionEtcRepository;


    @DisplayName("프리랜서 프로필 개발자 상세 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_개발자_상세조회() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java,spring", "backend");
        List<JavaSkill> javaSkills = Arrays.asList(JavaSkill.createJavaSkill(JavaDetailSkill.SPRING, developer), JavaSkill.createJavaSkill(JavaDetailSkill.BACK_END, developer));
        List<MobileAppSkill> mobileAppSkills = Arrays.asList(MobileAppSkill.createMobileAppSkill(MobileAppDetailSkill.ANDROID, developer));
        List<PhpOrAspSkill> phpOrAspSkills = Arrays.asList(PhpOrAspSkill.createPhpOrAspSkill(PhpOrAspDetailSkill.PHP, developer));
        List<DotNetSkill> dotNetSkills = Arrays.asList(DotNetSkill.createDotNetSkill(DotNetDetailSkill.C, developer));
        List<JavaScriptSkill> javaScriptSkills = Arrays.asList(JavaScriptSkill.createJavaScriptSkill(JavaScriptDetailSkill.ANGULAR_JS, developer));
        List<ClangSkill> cSkills = Arrays.asList(ClangSkill.createCSkill(CDetailSkill.EMBEDDED, developer));
        List<DBSkill> dbSkills = Arrays.asList(DBSkill.createDBSkill(DBDetailSkill.MARIADB, developer), DBSkill.createDBSkill(DBDetailSkill.MYSQL, developer));
        String etc = "etc";

        developer.coverDeveloperSkills(
                javaSkills,
                mobileAppSkills,
                phpOrAspSkills,
                dotNetSkills,
                javaScriptSkills,
                cSkills,
                dbSkills,
                etc
        );

        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        String path = FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DEVELOPER_FIND.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("focusSkills", hasSize(2)))
                .andExpect(jsonPath("roles", hasSize(1)))
                .andExpect(jsonPath("javaDetailSkills", hasSize(2)))
                .andExpect(jsonPath("mobileAppDetailSkills", hasSize(1)))
                .andExpect(jsonPath("phpOrAspDetailSkills", hasSize(1)))
                .andExpect(jsonPath("dotNetDetailSkills", hasSize(1)))
                .andExpect(jsonPath("javaScriptDetailSkills", hasSize(1)))
                .andExpect(jsonPath("cdetailSkills", hasSize(1)))
                .andExpect(jsonPath("dbDetailSkills", hasSize(2)))
                .andExpect(jsonPath("etcSkill").value("etc"))
                .andDo(print());
    }

    @DisplayName("프리랜서 프로필 퍼블리셔 상세 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_퍼블리셔_상세조회() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Publisher publisher = Publisher.createBasicPublisher(PositionType.PUBLISHER, freelancerProfile, "etcPubSkill");
        List<PublishingSkill> publishingSkillList = Arrays.asList(PublishingSkill.createPublishingSkill(PublishingDetailSkill.HTML5, publisher), PublishingSkill.createPublishingSkill(PublishingDetailSkill.CSS, publisher));
        publisher.coverPublishingSkill(publishingSkillList);
        freelancerProfile.coverPosition(publisher);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        String path = FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PUBLISHER_FIND.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("publishingDetailSkills", hasSize(2)))
                .andExpect(jsonPath("etcSkill").value("etcPubSkill"))
                .andDo(print());
    }

    @DisplayName("프리랜서 프로필 디자이너 상세 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_디자이너_상세조회() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Designer designer = Designer.createBasicDesigner(PositionType.DESIGNER, freelancerProfile);
        List<DesignRole> designRoles = Arrays.asList(DesignRole.createDesignRole(DesignDetailRole.APP_DESIGN, designer), DesignRole.createDesignRole(DesignDetailRole.GAME_DESIGN, designer));
        List<DesignSkill> designSkills = Arrays.asList(DesignSkill.createDesignSkill(DesignDetailSkill.FLASH, designer));
        String etcRole = "etcRole";
        String etcSkill = "etcSkill";
        designer.coverDesignRoleAndSkill(
                designRoles,
                designSkills,
                etcRole,
                etcSkill
        );
        freelancerProfile.coverPosition(designer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        String path = FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DESIGNER_FIND.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("designDetailRoles", hasSize(2)))
                .andExpect(jsonPath("etcRole").value(etcRole))
                .andExpect(jsonPath("designDetailSkills", hasSize(1)))
                .andExpect(jsonPath("etcSkill").value(etcSkill))
                .andDo(print());
    }

    @DisplayName("프리랜서 프로필 기획자 상세 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_기획자_상세조회() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile);
        List<PlannerField> plannerFields = Arrays.asList(PlannerField.createPlannerField(PlannerDetailField.APP_PLAN, planner), PlannerField.createPlannerField(PlannerDetailField.WEB_PLAN, planner));
        String etcField = "etcField";
        planner.coverAllField(plannerFields, etcField);
        freelancerProfile.coverPosition(planner);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        String path = FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PLANNER_FIND.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("plannerDetailFields", hasSize(2)))
                .andExpect(jsonPath("etcField").value(etcField))
                .andDo(print());
    }

    @DisplayName("프리랜서 프로필 기타포지션 상세 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_기타포지션_상세조회() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        PositionEtc positionEtc = PositionEtc.createBasicPositionEtc(PositionType.ETC, freelancerProfile);
        List<EtcRole> etcRoles = Arrays.asList(EtcRole.createEtcRole(EtcDetailRole.DBA, positionEtc));
        String positionEtcField = "positionEtcField";
        positionEtc.coverAllField(etcRoles, positionEtcField);
        freelancerProfile.coverPosition(positionEtc);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        String path = FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_ETC_FIND.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("etcDetailRoles", hasSize(1)))
                .andExpect(jsonPath("positionEtcRole").value(positionEtcField))
                .andDo(print());
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}
