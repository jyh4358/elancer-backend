package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponses;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.service.position.FreelancerPositionSearchService;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishfreelancer.model.WishFreelancer;
import com.example.elancer.wishfreelancer.repository.WishFreelancerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;

class FreelancerPositionSearchServiceTest extends ServiceBaseTest {

    @Autowired
    private FreelancerPositionSearchService freelancerPositionSearchService;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private WishFreelancerRepository wishFreelancerRepository;

    private MemberDetails memberDetails;

    private Freelancer freelancer1;

    @BeforeEach
    void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        freelancer1 = FreelancerHelper.프리랜서_생성_아이디(freelancerRepository, passwordEncoder, "id1");
        freelancer1.updateFreelancer(
                freelancer1.getName(),
                freelancer1.getPassword(),
                freelancer1.getEmail(),
                freelancer1.getPhone(),
                freelancer1.getWebsite(),
                freelancer1.getAddress(),
                freelancer1.getBirthDate(),
                3,
                3,
                0,
                100,
                new ArrayList<>(),
                null,
                null,
                freelancer1.getFreelancerAccountInfo().getMailReceptionState(),
                freelancer1.getFreelancerAccountInfo().getPresentWorkState(),
                HopeWorkState.AT_HOME,
                freelancer1.getFreelancerAccountInfo().getWorkPossibleState(),
                freelancer1.getFreelancerAccountInfo().getWorkStartPossibleDate(),
                freelancer1.getFreelancerAccountInfo().getHopeWorkCountry(),
                null
        );
        freelancerRepository.save(freelancer1);
        FreelancerProfile freelancerProfile1 = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer1, PositionType.DEVELOPER));
        developerRepository.deleteById(freelancerProfile1.getPosition().getNum());
        Developer javaDeveloper1 = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile1, "java,spring", "role"));
        freelancerProfile1.coverPosition(javaDeveloper1);

        Freelancer freelancer2 = FreelancerHelper.프리랜서_생성_아이디(freelancerRepository, passwordEncoder, "id2");
        freelancer2.updateFreelancer(
                freelancer2.getName(),
                freelancer2.getPassword(),
                freelancer2.getEmail(),
                freelancer2.getPhone(),
                freelancer2.getWebsite(),
                freelancer2.getAddress(),
                freelancer2.getBirthDate(),
                6,
                3,
                0,
                100,
                new ArrayList<>(),
                null,
                null,
                freelancer2.getFreelancerAccountInfo().getMailReceptionState(),
                freelancer2.getFreelancerAccountInfo().getPresentWorkState(),
                HopeWorkState.AT_COMPANY,
                freelancer2.getFreelancerAccountInfo().getWorkPossibleState(),
                freelancer2.getFreelancerAccountInfo().getWorkStartPossibleDate(),
                freelancer2.getFreelancerAccountInfo().getHopeWorkCountry(),
                null
        );
        freelancerRepository.save(freelancer2);
        FreelancerProfile freelancerProfile2 = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer2, PositionType.DEVELOPER));
        developerRepository.deleteById(freelancerProfile2.getPosition().getNum());
        Developer javaDeveloper2 = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile2, "java,spring,django", "role"));

        Freelancer freelancer3 = FreelancerHelper.프리랜서_생성_아이디(freelancerRepository, passwordEncoder, "id3");
        freelancer3.updateFreelancer(
                freelancer3.getName(),
                freelancer3.getPassword(),
                freelancer3.getEmail(),
                freelancer3.getPhone(),
                freelancer3.getWebsite(),
                freelancer3.getAddress(),
                freelancer3.getBirthDate(),
                11,
                3,
                0,
                100,
                new ArrayList<>(),
                null,
                null,
                freelancer3.getFreelancerAccountInfo().getMailReceptionState(),
                freelancer3.getFreelancerAccountInfo().getPresentWorkState(),
                HopeWorkState.AT_HOME,
                freelancer3.getFreelancerAccountInfo().getWorkPossibleState(),
                freelancer3.getFreelancerAccountInfo().getWorkStartPossibleDate(),
                freelancer3.getFreelancerAccountInfo().getHopeWorkCountry(),
                null
        );
        freelancerRepository.save(freelancer3);
        FreelancerProfile freelancerProfile3 = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer3, PositionType.DEVELOPER));
        developerRepository.deleteById(freelancerProfile3.getPosition().getNum());
        Developer javaDeveloper3 = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile3, "java,swing", "role"));


        Freelancer freelancer4 = FreelancerHelper.프리랜서_생성_아이디(freelancerRepository, passwordEncoder, "id4");
        freelancer4.updateFreelancer(
                freelancer4.getName(),
                freelancer4.getPassword(),
                freelancer4.getEmail(),
                freelancer4.getPhone(),
                freelancer4.getWebsite(),
                freelancer4.getAddress(),
                freelancer4.getBirthDate(),
                7,
                3,
                0,
                100,
                new ArrayList<>(),
                null,
                null,
                freelancer4.getFreelancerAccountInfo().getMailReceptionState(),
                freelancer4.getFreelancerAccountInfo().getPresentWorkState(),
                HopeWorkState.AT_HALF_COMPANY,
                freelancer4.getFreelancerAccountInfo().getWorkPossibleState(),
                freelancer4.getFreelancerAccountInfo().getWorkStartPossibleDate(),
                freelancer4.getFreelancerAccountInfo().getHopeWorkCountry(),
                null
        );
        freelancerRepository.save(freelancer4);
        FreelancerProfile freelancerProfile4 = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer4, PositionType.DEVELOPER));
        developerRepository.deleteById(freelancerProfile4.getPosition().getNum());
        Developer developer4 = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile4, "node.js,C", "role"));


        Freelancer freelancer5 = FreelancerHelper.프리랜서_생성_아이디(freelancerRepository, passwordEncoder, "id5");
        freelancer5.updateFreelancer(
                freelancer5.getName(),
                freelancer5.getPassword(),
                freelancer5.getEmail(),
                freelancer5.getPhone(),
                freelancer5.getWebsite(),
                freelancer5.getAddress(),
                freelancer5.getBirthDate(),
                13,
                3,
                0,
                100,
                new ArrayList<>(),
                null,
                null,
                freelancer5.getFreelancerAccountInfo().getMailReceptionState(),
                freelancer5.getFreelancerAccountInfo().getPresentWorkState(),
                HopeWorkState.AT_COMPANY,
                freelancer5.getFreelancerAccountInfo().getWorkPossibleState(),
                freelancer5.getFreelancerAccountInfo().getWorkStartPossibleDate(),
                freelancer5.getFreelancerAccountInfo().getHopeWorkCountry(),
                null
        );
        freelancerRepository.save(freelancer5);
        FreelancerProfile freelancerProfile5 = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer5, PositionType.DEVELOPER));
        developerRepository.deleteById(freelancerProfile5.getPosition().getNum());
        Developer developer5 = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile5, "mysql,python", "role"));

        tx.commit();

        entityManagerFactory.close();

        memberDetails = new MemberDetails(freelancer1.getNum(), freelancer1.getUserId(), freelancer1.getRole());
    }

    @DisplayName("개발자 목록을 주요 스킬만 검색한다.")
    @Test
    public void 개발자_주요스킬_검색() {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDevelopers(
                PositionType.DEVELOPER,
                Arrays.asList("java", "spring"),
                null,
                null,
                null,
                null,
                memberDetails
        );

        //then
        Assertions.assertThat(freelancerSimpleResponses.getFreelancerSimpleResponseList()).hasSize(2);
    }

    @DisplayName("개발자 목록 자바만 검색한다.")
    @Test
    public void 개발자_주요스킬_자바만_검색() {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDevelopers(
                PositionType.DEVELOPER,
                Arrays.asList("java"),
                null,
                null,
                null,
                null,
                memberDetails
        );

        //then
        Assertions.assertThat(freelancerSimpleResponses.getFreelancerSimpleResponseList()).hasSize(3);
    }

    @DisplayName("개발자 목록 노드만 검색한다.")
    @Test
    public void 개발자_주요스킬_노드만_검색() {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDevelopers(
                PositionType.DEVELOPER,
                Arrays.asList("node"),
                null,
                null,
                null,
                null,
                memberDetails
        );

        //then
        Assertions.assertThat(freelancerSimpleResponses.getFreelancerSimpleResponseList()).hasSize(1);
    }

    @DisplayName("개발자 목록 주요스킬과 주니어 경력으로 검색한다.")
    @Test
    public void 개발자_주요스킬_경력_검색_주니어() {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDevelopers(
                PositionType.DEVELOPER,
                null,
                null,
                null,
                Arrays.asList(PositionWorkManShip.JUNIOR),
                null,
                memberDetails
        );

        //then
        Assertions.assertThat(freelancerSimpleResponses.getFreelancerSimpleResponseList()).hasSize(1);
    }

    @DisplayName("개발자 목록 주요스킬과 미들 경력으로 검색한다.")
    @Test
    public void 개발자_주요스킬_경력_검색_미들() {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDevelopers(
                PositionType.DEVELOPER,
                null,
                null,
                null,
                Arrays.asList(PositionWorkManShip.MIDDLE),
                null,
                memberDetails
        );

        //then
        Assertions.assertThat(freelancerSimpleResponses.getFreelancerSimpleResponseList()).hasSize(2);
    }

    @DisplayName("개발자 목록 주요스킬과 시니어 경력으로 검색한다.")
    @Test
    public void 개발자_주요스킬_경력_검색_시니어() {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDevelopers(
                PositionType.DEVELOPER,
                null,
                null,
                null,
                Arrays.asList(PositionWorkManShip.SENIOR),
                null,
                memberDetails
        );

        //then
        Assertions.assertThat(freelancerSimpleResponses.getFreelancerSimpleResponseList()).hasSize(2);
    }

    @DisplayName("개발자 목록 주요스킬과 근무형태를 상주로 검색한다.")
    @Test
    public void 개발자_주요스킬_근무형태_상주() {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDevelopers(
                PositionType.DEVELOPER,
                null,
                null,
                Arrays.asList(HopeWorkState.AT_COMPANY),
                null,
                null,
                memberDetails
        );

        //then
        Assertions.assertThat(freelancerSimpleResponses.getFreelancerSimpleResponseList()).hasSize(2);
    }

    @DisplayName("개발자 목록 검색시 인재스크랩과 겹치면 해당 인스턴스의 좋아요가 활성된다.")
    @Test
    public void 개발자_검색_인재스크랩_좋아요_적용() {
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        WishFreelancer wishFreelancer = wishFreelancerRepository.save(WishFreelancer.createWishFreelancer(enterprise, freelancer1));
        MemberDetails enterpriseMemberDetails = MemberDetails.userDetailsFrom(enterprise);

        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDevelopers(
                PositionType.DEVELOPER,
                Arrays.asList("java", "spring"),
                null,
                null,
                null,
                null,
                enterpriseMemberDetails
        );

        //then
        Assertions.assertThat(freelancerSimpleResponses.getFreelancerSimpleResponseList()).hasSize(2);
        Assertions.assertThat(freelancerSimpleResponses.getFreelancerSimpleResponseList().get(0).isWishState()).isTrue();
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}