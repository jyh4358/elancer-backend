package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.dto.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
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
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.CSkillRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DbSkillRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DotNetSkillRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.JavaScriptSkillRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.JavaSkillRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.MobileAppSkillRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.PhpOrAspSkillRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.MemberType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("h2")
@SpringBootTest
class FreelancerPositionServiceTest {
    @Autowired
    private FreelancerPositionService freelancerPositionService;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private JavaSkillRepository javaSkillRepository;
    @Autowired
    private MobileAppSkillRepository mobileAppSkillRepository;
    @Autowired
    private PhpOrAspSkillRepository phpOrAspSkillRepository;
    @Autowired
    private DotNetSkillRepository dotNetSkillRepository;
    @Autowired
    private JavaScriptSkillRepository javaScriptSkillRepository;
    @Autowired
    private CSkillRepository cSkillRepository;
    @Autowired
    private DbSkillRepository dbSkillRepository;


    @DisplayName("프리랜서 프로필 스킬이 개발자로 등록된다.")
    @Test
    public void 프리랜서_프로필_스킬이_개발자로_등록된다() {
        //given
        String memberId = "memberId";
        Freelancer freelancer = freelancerRepository.save(Freelancer.createFreelancer(
                memberId,
                "pwd",
                "name",
                "phone",
                "email",
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        ));

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer));

        DeveloperCoverRequest developerCoverRequest = new DeveloperCoverRequest(
                "Java",
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

        MemberDetails memberDetails = new MemberDetails(memberId);

        //when
        freelancerPositionService.coverFreelancerPositionToDeveloper(freelancerProfile.getNum(), memberDetails, developerCoverRequest);

        //then
        List<Developer> developers = developerRepository.findAll();
        Assertions.assertThat(developers).hasSize(1);
        Assertions.assertThat(developers.get(0).getFocusSkill()).isEqualTo(developerCoverRequest.getFocusSkill());
        Assertions.assertThat(developers.get(0).getRole()).isEqualTo(developerCoverRequest.getRole());

        List<JavaSkill> javaSkills = javaSkillRepository.findAll();
        Assertions.assertThat(javaSkills).hasSize(2);

        List<MobileAppSkill> mobileAppSkills = mobileAppSkillRepository.findAll();
        Assertions.assertThat(mobileAppSkills).hasSize(1);

        List<PhpOrAspSkill> phpOrAspSkills = phpOrAspSkillRepository.findAll();
        Assertions.assertThat(phpOrAspSkills).hasSize(1);

        List<DotNetSkill> dotNetSkills = dotNetSkillRepository.findAll();
        Assertions.assertThat(dotNetSkills).hasSize(1);

        List<JavaScriptSkill> javaScriptSkills = javaScriptSkillRepository.findAll();
        Assertions.assertThat(javaScriptSkills).hasSize(1);

        List<CSkill> cSkills = cSkillRepository.findAll();
        Assertions.assertThat(cSkills).hasSize(1);

        List<DBSkill> dbSkills = dbSkillRepository.findAll();
        Assertions.assertThat(dbSkills).hasSize(2);
    }
}