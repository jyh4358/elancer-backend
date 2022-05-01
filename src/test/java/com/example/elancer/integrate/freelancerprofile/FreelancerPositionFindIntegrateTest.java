package com.example.elancer.integrate.freelancerprofile;


import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.controller.FreelancerPositionControllerPath;
import com.example.elancer.freelancerprofile.controller.FreelancerPositionFindControllerPath;
import com.example.elancer.freelancerprofile.dto.request.position.DesignerCoverRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailSkill;
import com.example.elancer.freelancerprofile.model.position.designer.DesignRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignSkill;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CSkill;
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
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerDetailField;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerField;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingDetailSkill;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingSkill;
import com.example.elancer.freelancerprofile.repository.position.designer.DesignerRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.planner.PlannerRepository;
import com.example.elancer.freelancerprofile.repository.position.publisher.PublisherRepository;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import org.assertj.core.api.Assertions;
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


    @DisplayName("프리랜서 프로필 개발자 상세 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_개발자_상세조회() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java,spring", "backend");
        List<JavaSkill> javaSkills = Arrays.asList(JavaSkill.createJavaSkill(JavaDetailSkill.SPRING, developer), JavaSkill.createJavaSkill(JavaDetailSkill.BACK_END, developer));
        List<MobileAppSkill> mobileAppSkills = Arrays.asList(MobileAppSkill.createMobileAppSkill(MobileAppDetailSkill.ANDROID, developer));
        List<PhpOrAspSkill> phpOrAspSkills = Arrays.asList(PhpOrAspSkill.createPhpOrAspSkill(PhpOrAspDetailSkill.PHP, developer));
        List<DotNetSkill> dotNetSkills = Arrays.asList(DotNetSkill.createDotNetSkill(DotNetDetailSkill.C, developer));
        List<JavaScriptSkill> javaScriptSkills = Arrays.asList(JavaScriptSkill.createJavaScriptSkill(JavaScriptDetailSkill.ANGULAR_JS, developer));
        List<CSkill> cSkills = Arrays.asList(CSkill.createCSkill(CDetailSkill.EMBEDDED, developer));
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

        developerRepository.save(developer);

        //when & then
        String path = FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DEVELOPER_FIND.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
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
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        Publisher publisher = Publisher.createBasicPublisher(PositionType.PUBLISHER, freelancerProfile, "etcPubSkill");
        List<PublishingSkill> publishingSkillList = Arrays.asList(PublishingSkill.createPublishingSkill(PublishingDetailSkill.HTML5, publisher), PublishingSkill.createPublishingSkill(PublishingDetailSkill.CSS, publisher));
        publisher.coverPublishingSkill(publishingSkillList);

        publisherRepository.save(publisher);

        //when & then
        String path = FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PUBLISHER_FIND.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("publishingDetailSkills", hasSize(2)))
                .andExpect(jsonPath("etcSkill").value("etcPubSkill"))
                .andDo(print());
    }

    @DisplayName("프리랜서 프로필 디자이너 상세 조회 통합테스트")
    @Test
    public void 프리랜서_프로필_디자이너_상세조회() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

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
        designerRepository.save(designer);

        //when & then
        String path = FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DESIGNER_FIND.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
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
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile);
        List<PlannerField> plannerFields = Arrays.asList(PlannerField.createPlannerField(PlannerDetailField.APP_PLAN, planner), PlannerField.createPlannerField(PlannerDetailField.WEB_PLAN, planner));
        String etcField = "etcField";
        planner.coverAllField(plannerFields, etcField);
        plannerRepository.save(planner);

        //when & then
        String path = FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PLANNER_FIND.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("plannerDetailFields", hasSize(2)))
                .andExpect(jsonPath("etcField").value(etcField))
                .andDo(print());
    }
}
