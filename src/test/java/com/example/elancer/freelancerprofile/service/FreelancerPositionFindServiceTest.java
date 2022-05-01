package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancerprofile.dtd.PublisherResponse;
import com.example.elancer.freelancerprofile.dto.DesignerResponse;
import com.example.elancer.freelancerprofile.dto.DeveloperResponse;
import com.example.elancer.freelancerprofile.dto.PlannerResponse;
import com.example.elancer.freelancerprofile.dto.request.position.DeveloperCoverRequest;
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
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.position.designer.DesignerRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.planner.PlannerRepository;
import com.example.elancer.freelancerprofile.repository.position.publisher.PublisherRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("h2")
@ExtendWith(value = MockitoExtension.class)
class FreelancerPositionFindServiceTest {
    private FreelancerPositionFindService freelancerPositionFindService;

    @Mock
    private FreelancerProfileRepository freelancerProfileRepository;

    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private DesignerRepository designerRepository;
    @Mock
    private PlannerRepository plannerRepository;

    @BeforeEach
    void setUp() {
        this.freelancerPositionFindService = new FreelancerPositionFindService(
                freelancerProfileRepository, developerRepository, publisherRepository, designerRepository, plannerRepository
        );
    }

    @DisplayName("프리랜서 프로필 개발자 포지션을 상세조회한다.")
    @Test
    public void 프리랜서_프로필_개발자_상세조회() {
        //given
        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", null);

        Long profileNum = 1L;

        MemberDetails memberDetails = new MemberDetails(null);

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java, spring", "backend");
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

        when(freelancerProfileRepository.findById(any())).thenReturn(Optional.of(freelancerProfile));
        when(developerRepository.findByFreelancerProfileNum(any())).thenReturn(Optional.of(developer));

        //when
        DeveloperResponse developerResponse = freelancerPositionFindService.coverFreelancerPositionToDeveloper(profileNum, memberDetails);

        //then
        Assertions.assertThat(developerResponse.getFocusSkills().get(0)).isEqualTo(StringEditor.editStringToStringList(developer.getFocusSkill()).get(0));
        Assertions.assertThat(developerResponse.getFocusSkills().get(1)).isEqualTo(StringEditor.editStringToStringList(developer.getFocusSkill()).get(1));
        Assertions.assertThat(developerResponse.getRoles().get(0)).isEqualTo(StringEditor.editStringToStringList(developer.getRole()).get(0));
        Assertions.assertThat(developerResponse.getJavaDetailSkills().get(0)).isEqualTo(javaSkills.get(0).getJavaDetailSkill());
        Assertions.assertThat(developerResponse.getJavaDetailSkills().get(1)).isEqualTo(javaSkills.get(1).getJavaDetailSkill());
        Assertions.assertThat(developerResponse.getMobileAppDetailSkills().get(0)).isEqualTo(mobileAppSkills.get(0).getMobileAppDetailSkill());
        Assertions.assertThat(developerResponse.getPhpOrAspDetailSkills().get(0)).isEqualTo(phpOrAspSkills.get(0).getPhpOrAspDetailSkill());
        Assertions.assertThat(developerResponse.getDotNetDetailSkills().get(0)).isEqualTo(dotNetSkills.get(0).getDotNetDetailSkill());
        Assertions.assertThat(developerResponse.getJavaScriptDetailSkills().get(0)).isEqualTo(javaScriptSkills.get(0).getJavaScriptDetailSkill());
        Assertions.assertThat(developerResponse.getCDetailSkills().get(0)).isEqualTo(cSkills.get(0).getCDetailSkill());
        Assertions.assertThat(developerResponse.getDbDetailSkills().get(0)).isEqualTo(dbSkills.get(0).getDbDetailSkill());
        Assertions.assertThat(developerResponse.getEtcSkill()).isEqualTo(etc);
    }

    @DisplayName("프리랜서 프로필 퍼블리셔 포지션을 상세조회한다.")
    @Test
    public void 프리랜서_프로필_퍼블리셔_상세조회() {
        //given
        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", null);

        Long profileNum = 1L;

        MemberDetails memberDetails = new MemberDetails(null);

        Publisher publisher = Publisher.createBasicPublisher(PositionType.PUBLISHER, freelancerProfile, "etcPubSkill");
        List<PublishingSkill> publishingSkillList = Arrays.asList(PublishingSkill.createPublishingSkill(PublishingDetailSkill.HTML5, publisher), PublishingSkill.createPublishingSkill(PublishingDetailSkill.CSS, publisher));
        publisher.coverPublishingSkill(publishingSkillList);

        when(freelancerProfileRepository.findById(any())).thenReturn(Optional.of(freelancerProfile));
        when(publisherRepository.findByFreelancerProfileNum(any())).thenReturn(Optional.of(publisher));

        //when
        PublisherResponse publisherResponse = freelancerPositionFindService.coverFreelancerPositionToPublisher(profileNum, memberDetails);

        //then
        Assertions.assertThat(publisherResponse.getPublishingDetailSkills().get(0)).isEqualTo(publishingSkillList.get(0).getPublishingDetailSkill());
        Assertions.assertThat(publisherResponse.getPublishingDetailSkills().get(1)).isEqualTo(publishingSkillList.get(1).getPublishingDetailSkill());
        Assertions.assertThat(publisherResponse.getEtcSkill()).isEqualTo("etcPubSkill");
    }

    @DisplayName("프리랜서 프로필 디자이너 포지션을 상세조회한다.")
    @Test
    public void 프리랜서_프로필_디자이너_상세조회() {
        //given
        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", null);

        Long profileNum = 1L;

        MemberDetails memberDetails = new MemberDetails(null);

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

        when(freelancerProfileRepository.findById(any())).thenReturn(Optional.of(freelancerProfile));
        when(designerRepository.findByFreelancerProfileNum(any())).thenReturn(Optional.of(designer));

        //when
        DesignerResponse designerResponse = freelancerPositionFindService.coverFreelancerPositionToDesigner(profileNum, memberDetails);

        //then
        Assertions.assertThat(designerResponse.getDesignDetailRoles().get(0)).isEqualTo(designRoles.get(0).getDesignDetailRole());
        Assertions.assertThat(designerResponse.getDesignDetailRoles().get(1)).isEqualTo(designRoles.get(1).getDesignDetailRole());
        Assertions.assertThat(designerResponse.getEtcRole()).isEqualTo(etcRole);
        Assertions.assertThat(designerResponse.getDesignDetailSkills().get(0)).isEqualTo(designSkills.get(0).getDesignDetailSkill());
        Assertions.assertThat(designerResponse.getEtcSkill()).isEqualTo(etcSkill);
    }

    @DisplayName("프리랜서 프로필 기획자 포지션을 상세조회한다.")
    @Test
    public void 프리랜서_프로필_기획자_상세조회() {
        //given
        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", null);

        Long profileNum = 1L;

        MemberDetails memberDetails = new MemberDetails(null);

        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile);
        List<PlannerField> plannerFields = Arrays.asList(PlannerField.createPlannerField(PlannerDetailField.APP_PLAN, planner), PlannerField.createPlannerField(PlannerDetailField.WEB_PLAN, planner));
        String etcField = "etcField";
        planner.coverAllField(plannerFields, etcField);

        when(freelancerProfileRepository.findById(any())).thenReturn(Optional.of(freelancerProfile));
        when(plannerRepository.findByFreelancerProfileNum(any())).thenReturn(Optional.of(planner));

        //when
        PlannerResponse plannerResponse = freelancerPositionFindService.coverFreelancerPositionToPlanner(profileNum, memberDetails);

        //then
        Assertions.assertThat(plannerResponse.getPlannerDetailFields().get(0)).isEqualTo(plannerFields.get(0).getPlannerDetailField());
        Assertions.assertThat(plannerResponse.getPlannerDetailFields().get(1)).isEqualTo(plannerFields.get(1).getPlannerDetailField());
        Assertions.assertThat(plannerResponse.getEtcField()).isEqualTo(etcField);
    }

}