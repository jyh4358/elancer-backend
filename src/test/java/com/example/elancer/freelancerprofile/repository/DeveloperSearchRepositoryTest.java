package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerWorkType;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.model.KOSAState;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.PresentWorkState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.model.WorkType;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancer.repository.FreelancerWorkTypeRepository;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    private FreelancerWorkTypeRepository freelancerWorkTypeRepository;


    @Autowired
    private DeveloperSearchRepository developerSearchRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("개발자가 조건에 맞게 검색됨.")
    //TODO tdd후 구현이 안됨.
//    @Test
    public void 개발자_검색() {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("hi!", freelancer, PositionType.DEVELOPER));
        Developer developer = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java,spring", "백엔드"));

        freelancerProfile.coverPosition(developer);

        List<WorkType> workTypes = freelancerWorkTypeRepository.saveAll(Arrays.asList(WorkType.createWorkType(FreelancerWorkType.ACCOUNTING, freelancer), WorkType.createWorkType(FreelancerWorkType.BIGDATA, freelancer)));
        freelancer.updateFreelancer(
                "멤버이름",
                "패스워드",
                "email@email.email.com",
                "010-0101-0101",
                "http://web.com",
                new Address(CountryType.KR, "경기도", "성남시", "중원구"),
                LocalDate.of(2000, 01, 01),
                9,
                5,
                400,
                600,
                workTypes,
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

        //when
        Slice<FreelancerSimpleResponse> responses = developerSearchRepository.findFreelancerProfileByFetch(
                PositionType.DEVELOPER,
                Arrays.asList("java,spring"),
                "backend",
                Arrays.asList(HopeWorkState.AT_COMPANY, HopeWorkState.AT_HOME),
                Arrays.asList(PositionWorkManShip.MIDDLE)
        );

        //then
        Assertions.assertThat(responses.getContent().get(0).getFreelancerName()).isEqualTo(freelancer.getName());
    }

}