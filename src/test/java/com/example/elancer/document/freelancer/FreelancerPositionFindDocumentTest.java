package com.example.elancer.document.freelancer;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.controller.FreelancerPositionControllerPath;
import com.example.elancer.freelancerprofile.controller.FreelancerPositionFindControllerPath;
import com.example.elancer.freelancerprofile.dto.request.position.DesignerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PlannerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PositionEtcCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PublisherCoverRequest;
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
import com.example.elancer.freelancerprofile.model.position.etc.EtcDetailRole;
import com.example.elancer.freelancerprofile.model.position.etc.EtcRole;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerDetailField;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerField;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingDetailSkill;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingSkill;
import com.example.elancer.freelancerprofile.repository.academic.AcademicRepository;
import com.example.elancer.freelancerprofile.repository.career.CareerRepository;
import com.example.elancer.freelancerprofile.repository.education.EducationRepository;
import com.example.elancer.freelancerprofile.repository.language.LanguageRepository;
import com.example.elancer.freelancerprofile.repository.license.LicenseRepository;
import com.example.elancer.freelancerprofile.repository.position.designer.DesignerRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.etc.PositionEtcRepository;
import com.example.elancer.freelancerprofile.repository.position.planner.PlannerRepository;
import com.example.elancer.freelancerprofile.repository.position.publisher.PublisherRepository;
import com.example.elancer.freelancerprofile.repository.projecthistory.ProjectHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerPositionFindDocumentTest extends DocumentBaseTest {

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


    @DisplayName("프리랜서 프로필 개발자 포지션 조회 문서화")
    @Test
    public void 프리랜서_프로필_개발자_포지션_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

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

        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DEVELOPER_FIND, freelancerProfile.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-developer-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("focusSkills").type("List<String>").description("프리랜서 개발자 주언어 정보 필드."),
                                fieldWithPath("roles").type("List<String>").description("프리랜서 개발자 역할 정보 필드."),
                                fieldWithPath("javaDetailSkills").type("List<JavaDetailSkill>").description("프리랜서 개발자 JAVA 스킬 정보 필드."),
                                fieldWithPath("mobileAppDetailSkills").type("List<MobileAppDetailSkill>").description("프리랜서 개발자 Mobile App 스킬 정보 필드."),
                                fieldWithPath("phpOrAspDetailSkills").type("List<PhpOrAspDetailSkill>").description("프리랜서 개발자 PHP/ASP 스킬 정보 필드."),
                                fieldWithPath("dotNetDetailSkills").type("List<DotNetDetailSkill>").description("프리랜서 개발자 .NET 스킬 정보 필드."),
                                fieldWithPath("javaScriptDetailSkills").type("List<JavaScriptDetailSkill>").description("프리랜서 개발자 JavaScript 스킬 정보 필드."),
                                fieldWithPath("cdetailSkills").type("List<CDetailSkill>").description("프리랜서 개발자 C/C++ 스킬 정보 필드."),
                                fieldWithPath("dbDetailSkills").type("List<DBDetailSkill>").description("프리랜서 개발자 DB 스킬 정보 필드."),
                                fieldWithPath("etcSkill").type("String").description("프리랜서 개발자 기티스킬 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 퍼블리셔 포지션 조회 문서화")
    @Test
    public void 프리랜서_프로필_퍼블리셔_포지션_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Publisher publisher = Publisher.createBasicPublisher(PositionType.PUBLISHER, freelancerProfile, "etcPubSkill");
        List<PublishingSkill> publishingSkillList = Arrays.asList(PublishingSkill.createPublishingSkill(PublishingDetailSkill.HTML5, publisher), PublishingSkill.createPublishingSkill(PublishingDetailSkill.CSS, publisher));
        publisher.coverPublishingSkill(publishingSkillList);
        freelancerProfile.coverPosition(publisher);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PUBLISHER_FIND, freelancerProfile.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-publisher-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("publishingDetailSkills").type("List<PublishingDetailSkill>").description("프리랜서 퍼블리셔 스킬,경험 정보 필드."),
                                fieldWithPath("etcSkill").type("String").description("프리랜서 퍼블리셔 기티스킬 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 디자이너 포지션 조회 문서화")
    @Test
    public void 프리랜서_프로필_디자이너_포지션_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
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
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DESIGNER_FIND, freelancerProfile.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-designer-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("designDetailRoles").type("List<DesignDetailRole>").description("프리랜서 디자이너 역할 정보 필드."),
                                fieldWithPath("etcRole").type("String").description("프리랜서 디자이너 기티역할 정보 필드."),
                                fieldWithPath("designDetailSkills").type("List<DesignDetailSkill>").description("프리랜서 디자이너 스킬 정보 필드."),
                                fieldWithPath("etcSkill").type("String").description("프리랜서 디자이너 기티스킬 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 기획자 포지션 조회 문서화")
    @Test
    public void 프리랜서_프로필_기획자_포지션_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile);
        List<PlannerField> plannerFields = Arrays.asList(PlannerField.createPlannerField(PlannerDetailField.APP_PLAN, planner), PlannerField.createPlannerField(PlannerDetailField.WEB_PLAN, planner));
        String etcField = "etcField";
        planner.coverAllField(plannerFields, etcField);
        freelancerProfile.coverPosition(planner);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PLANNER_FIND, freelancerProfile.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-planner-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("plannerDetailFields").type("List<PlannerDetailField>").description("프리랜서 기획자 업무분야 정보 필드."),
                                fieldWithPath("etcField").type("String").description("프리랜서 기획자 기티분야 정보 필드.")
                        )
                ));
    }

    @DisplayName("프리랜서 프로필 기타 포지션 조회 문서화")
    @Test
    public void 프리랜서_프로필_기타_포지션_조회_문서화() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        PositionEtc positionEtc = PositionEtc.createBasicPositionEtc(PositionType.ETC, freelancerProfile);
        List<EtcRole> etcRoles = Arrays.asList(EtcRole.createEtcRole(EtcDetailRole.DBA, positionEtc));
        String positionEtcField = "positionEtcField";
        freelancerProfile.coverPosition(positionEtc);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_ETC_FIND, freelancerProfile.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-etc-find",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
                        ),
                        responseFields(
                                fieldWithPath("etcDetailRoles").type("List<EtcDetailRole>").description("프리랜서 기티파트 역할 정보 필드."),
                                fieldWithPath("positionEtcRole").type("String").description("프리랜서 기타파트 기티분야 역할외 정보 필드.")
                        )
                ));
    }

    @AfterEach
    void tearDown() {
        databaseClean.clean();
    }
}
