package com.example.elancer.integrate.freelancerprofile;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.controller.FreelancerPositionControllerPath;
import com.example.elancer.freelancerprofile.dto.request.position.DesignerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PlannerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PositionEtcCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PublisherCoverRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.CrowdWorker;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailSkill;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspDetailSkill;
import com.example.elancer.freelancerprofile.model.position.etc.EtcDetailRole;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerDetailField;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingDetailSkill;
import com.example.elancer.freelancerprofile.repository.position.CrowdWorkerRepository;
import com.example.elancer.freelancerprofile.repository.position.designer.DesignerRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.etc.PositionEtcRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerPositionIntegrateTest extends IntegrateBaseTest {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private DesignerRepository designerRepository;

    @Autowired
    private PlannerRepository plannerRepository;

    @Autowired
    private CrowdWorkerRepository crowdWorkerRepository;

    @Autowired
    private PositionEtcRepository positionEtcRepository;


    @DisplayName("프리랜서 프로필 개발자 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_개발자_저장() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        DeveloperCoverRequest developerCoverRequest = new DeveloperCoverRequest(
                "Java, Spring",
                "백엔드 개발자",
                Arrays.asList(JavaDetailSkill.SPRING, JavaDetailSkill.BACK_END),
                Arrays.asList(MobileAppDetailSkill.ANDROID),
                Arrays.asList(PhpOrAspDetailSkill.PHP),
                Arrays.asList(DotNetDetailSkill.C),
                Arrays.asList(JavaScriptDetailSkill.ANGULAR_JS),
                Arrays.asList(CDetailSkill.EMBEDDED),
                Arrays.asList(DBDetailSkill.MARIADB, DBDetailSkill.MYSQL),
                "etc"
        );

        //when
        String path = FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_DEVELOPER_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(developerCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        List<Developer> developers = developerRepository.findAll();
        Assertions.assertThat(developers).hasSize(1);
    }

    @DisplayName("프리랜서 프로필 퍼블리셔 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_퍼블리셔_저장() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        PublisherCoverRequest publisherCoverRequest
                = new PublisherCoverRequest(Arrays.asList(PublishingDetailSkill.HTML5, PublishingDetailSkill.CSS, PublishingDetailSkill.JQUERY), "etcSkill");

        //when
        String path = FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_PUBLISHER_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(publisherCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        List<Publisher> publishers = publisherRepository.findAll();
        Assertions.assertThat(publishers).hasSize(1);
    }

    @DisplayName("프리랜서 프로필 디자이너 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_디자이너_저장() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        DesignerCoverRequest designerCoverRequest = new DesignerCoverRequest(
                Arrays.asList(DesignDetailRole.APP_DESIGN, DesignDetailRole.GAME_DESIGN),
                "etcRole",
                Arrays.asList(DesignDetailSkill.AFERE_EFFECT, DesignDetailSkill.THREE_D_MAX_AND_MAYA),
                "etcSkill"
        );

        //when
        String path = FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_DESIGNER_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(designerCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        List<Designer> designers = designerRepository.findAll();
        Assertions.assertThat(designers).hasSize(1);
    }

    @DisplayName("프리랜서 프로필 기획자 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_기획자_저장() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        PlannerCoverRequest plannerCoverRequest = new PlannerCoverRequest(Arrays.asList(PlannerDetailField.ACCOUNTING, PlannerDetailField.APP_PLAN), "etcField");

        //when
        String path = FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_PLANNER_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(plannerCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        List<Planner> planners = plannerRepository.findAll();
        Assertions.assertThat(planners).hasSize(1);
    }

    @DisplayName("프리랜서 프로필 크라우드워커 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_크라우드워커_저장() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        //when
        String path = FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_CROWD_WORKER_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        List<CrowdWorker> crowdWorkers = crowdWorkerRepository.findAll();
        Assertions.assertThat(crowdWorkers).hasSize(1);
    }

    @DisplayName("프리랜서 프로필 스킬-기타 저장 통합테스트")
    @Test
    public void 프리랜서_프로필_기타_저장() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.프리랜서_생성(freelancerRepository));
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        PositionEtcCoverRequest positionEtcCoverRequest = new PositionEtcCoverRequest(Arrays.asList(EtcDetailRole.AA, EtcDetailRole.DBA), "positionEtcRole");

        //when
        String path = FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_ETC_COVER.replace("{profileNum}", String.valueOf(freelancerProfile.getNum()));
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(positionEtcCoverRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        List<PositionEtc> positionEtcs = positionEtcRepository.findAll();
        Assertions.assertThat(positionEtcs).hasSize(1);
    }
}
