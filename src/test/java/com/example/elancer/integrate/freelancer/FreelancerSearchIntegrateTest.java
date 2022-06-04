package com.example.elancer.integrate.freelancer;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.controller.FreelancerPositionSearchControllerPath;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerSearchIntegrateTest extends IntegrateBaseTest {

    @Autowired
    private DeveloperRepository developerRepository;

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
    }

    @DisplayName("프리랜서 개발자 검색 통합테스트")
    @Test
    public void 프래랜서_개발자_검색_통합테스트() throws Exception {
        //given & when
        mockMvc.perform(get(FreelancerPositionSearchControllerPath.FREELANCER_DEVELOPER_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("positionType", String.valueOf(PositionType.DEVELOPER))
                        .param("majorSkillKeywords", "java", "spring")
                        .param("minorSkill", "")
                        .param("hopeWorkStates", "")
                        .param("positionWorkManShips", "")
                        .param("workArea", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("freelancerSimpleResponseList", hasSize(3)))
                .andDo(print())
                .andReturn();
        //then
    }
}
