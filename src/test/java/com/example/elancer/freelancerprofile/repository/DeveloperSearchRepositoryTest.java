package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

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
    private DeveloperSearchRepository developerSearchRepository;

    @DisplayName("개발자가 조건에 맞게 검색됨.")
    @Test
    public void 개발자_검색() {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("hi!", freelancer));
        Developer developer = developerRepository.save(Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java,spring", "백엔드"));


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