package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.model.KOSAState;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.PresentWorkState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
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
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.positionsearch.DeveloperSearchRepository;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("h2")
class DeveloperSearchRepositoryTest {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private DeveloperSearchRepository developerSearchRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

    @DisplayName("개발자가 조건에 맞게 검색됨.")
    @Test
//    @Transactional
    public void 개발자_검색() {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        FreelancerProfile freelancerProfile = new FreelancerProfile("hi!", freelancer, PositionType.DEVELOPER);
        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java,spring", "backend");
        List<JavaSkill> javaSkills = new ArrayList<>();
        javaSkills.addAll(Arrays.asList(JavaSkill.createJavaSkill(JavaDetailSkill.SPRING, developer), JavaSkill.createJavaSkill(JavaDetailSkill.BACK_END, developer)));
        List<MobileAppSkill> mobileAppSkills = new ArrayList<>();
        mobileAppSkills.addAll(Arrays.asList(MobileAppSkill.createMobileAppSkill(MobileAppDetailSkill.ANDROID, developer)));
        List<PhpOrAspSkill> phpOrAspSkills = new ArrayList<>();
        phpOrAspSkills.addAll(Arrays.asList(PhpOrAspSkill.createPhpOrAspSkill(PhpOrAspDetailSkill.PHP, developer)));
        List<DotNetSkill> dotNetSkills = new ArrayList<>();
        dotNetSkills.addAll(Arrays.asList(DotNetSkill.createDotNetSkill(DotNetDetailSkill.C, developer)));
        List<JavaScriptSkill> javaScriptSkills = new ArrayList<>();
        javaScriptSkills.addAll(Arrays.asList(JavaScriptSkill.createJavaScriptSkill(JavaScriptDetailSkill.ANGULAR_JS, developer)));
        List<ClangSkill> cSkills = new ArrayList<>();
        cSkills.addAll(Arrays.asList(ClangSkill.createCSkill(CDetailSkill.EMBEDDED, developer)));
        List<DBSkill> dbSkills = new ArrayList<>();
        dbSkills.addAll(Arrays.asList(DBSkill.createDBSkill(DBDetailSkill.MARIADB, developer), DBSkill.createDBSkill(DBDetailSkill.MYSQL, developer)));
        String etc = "etc1";



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

        freelancer.updateFreelancer(
                "멤버이름",
                "패스워드",
                "email@email.email.com",
                "010-0101-0101",
                "http://web.com",
                new Address(CountryType.KR, "", "서울시 강남구", "중원구"),
                LocalDate.of(2000, 01, 01),
                9,
                5,
                400,
                600,
                new ArrayList<>(),
                null,
                KOSAState.NOT_POSSESS,
                MailReceptionState.RECEPTION,
                PresentWorkState.FREE_AT_COMPANY,
                HopeWorkState.AT_COMPANY,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2022, 02, 01),
                CountryType.KR,
                "seoul"
        );

        Freelancer savedFreelancer = freelancerRepository.save(freelancer);
        FreelancerProfile savedFreelancerProfile = freelancerProfileRepository.save(freelancerProfile);
        Developer savedDeveloper = developerRepository.save(developer);

        Freelancer freelancer2 = FreelancerHelper.프리랜서_생성_아이디(freelancerRepository, passwordEncoder, "id2");
        FreelancerProfile freelancerProfile2 = freelancerProfileRepository.save(new FreelancerProfile("hi!", freelancer2, PositionType.DEVELOPER));
        Developer developer2 = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile2, "node.js,javaScript", "백엔드");
        List<PhpOrAspSkill> phpOrAspSkills2 = new ArrayList<>();
        phpOrAspSkills2.addAll(Arrays.asList(PhpOrAspSkill.createPhpOrAspSkill(PhpOrAspDetailSkill.ASP, developer)));
        List<JavaScriptSkill> javaScriptSkills2 = new ArrayList<>();
        javaScriptSkills2.addAll(Arrays.asList(JavaScriptSkill.createJavaScriptSkill(JavaScriptDetailSkill.NODE_JS, developer)));
        List<DBSkill> dbSkills2 = new ArrayList<>();
        dbSkills2.addAll(Arrays.asList(DBSkill.createDBSkill(DBDetailSkill.ORACLE, developer), DBSkill.createDBSkill(DBDetailSkill.MYSQL, developer)));
        String etc2 = "etc2";

        developer2.coverDeveloperSkills(
                new ArrayList<>(),
                new ArrayList<>(),
                phpOrAspSkills2,
                new ArrayList<>(),
                javaScriptSkills2,
                new ArrayList<>(),
                dbSkills2,
                etc2
        );

        freelancerProfile2.coverPosition(developer2);

        freelancer2.updateFreelancer(
                "멤버이름2",
                "패스워드2",
                "email2@email.email.com",
                "010-0202-020",
                null,
                new Address(CountryType.KR, "경기도", "성남시", "중원구"),
                LocalDate.of(2000, 01, 01),
                2,
                5,
                200,
                300,
                new ArrayList<>(),
                null,
                KOSAState.NOT_POSSESS,
                MailReceptionState.RECEPTION,
                PresentWorkState.FREE_AT_COMPANY,
                HopeWorkState.REGULAR,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2022, 02, 01),
                CountryType.KR,
                "seoul"
        );

        Developer savedDeveloper2 = developerRepository.save(developer2);
//        FreelancerProfile sfp2 = freelancerProfileRepository.save(freelancerProfile2);
//        Freelancer sf2 = freelancerRepository.save(freelancer2);


        Freelancer freelancer3 = FreelancerHelper.프리랜서_생성_아이디(freelancerRepository, passwordEncoder, "id3");
        FreelancerProfile freelancerProfile3 = freelancerProfileRepository.save(new FreelancerProfile("hi!", freelancer3, PositionType.DEVELOPER));
        Developer developer3 = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile3, "react,java", "backend,백엔드"));

        freelancerProfile3.coverPosition(developer3);

        freelancer3.updateFreelancer(
                "멤버이름3",
                "패스워드3",
                "email2@email.email.com",
                "010-0202-020",
                null,
                new Address(CountryType.KR, "경기도", "성남시", "중원구"),
                LocalDate.of(2000, 01, 01),
                5,
                5,
                200,
                300,
                new ArrayList<>(),
                null,
                KOSAState.NOT_POSSESS,
                MailReceptionState.RECEPTION,
                PresentWorkState.FREE_AT_COMPANY,
                HopeWorkState.AT_COMPANY,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2022, 02, 01),
                CountryType.KR,
                "seoul"
        );

        Developer savedDeveloper3 = developerRepository.save(developer3);
//        FreelancerProfile sfp3 = freelancerProfileRepository.save(freelancerProfile3);
//        Freelancer sf3 = freelancerRepository.save(freelancer3);

//        em.flush();
//        em.clear();

        //when
        Developer developer1 = developerRepository.findById(savedDeveloper3.getNum()).get();
        List<Developer> all = developerRepository.findAll();
        PageRequest pageable = PageRequest.of(0, 10);
        Slice<Developer> freelancers = developerSearchRepository.searchDevelopers(
                PositionType.DEVELOPER,
                "java",
                HopeWorkState.AT_COMPANY,
                null/*Arrays.asList(PositionWorkManShip.MIDDLE)*/,
                pageable, null/*WorkArea.SEOUL.getDesc()*/
        );

        //일단 생각대로 동작은 함. 다만 java를 검색하면 javaScript의 주스킬 가진 사람도 나오긴하는데 이건 흔한거라..., 별개로 검색시 develper객체에 freelancer나 profile 내에 데이터가 없었던 현상은 em.clear랑 연관되있다고 판단.
        //then
        Assertions.assertThat(freelancers.getContent()).hasSize(1);
    }

}