package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancerprofile.dto.DeveloperResponse;
import com.example.elancer.freelancerprofile.dto.request.position.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
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
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
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

    @BeforeEach
    void setUp() {
        this.freelancerPositionFindService = new FreelancerPositionFindService(
                freelancerProfileRepository, developerRepository
        );
    }

    @DisplayName("프리랜서 프로필 개발자 포지션 상세조회한다.")
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

}