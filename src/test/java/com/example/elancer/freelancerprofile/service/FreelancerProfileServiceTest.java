package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancer.dto.FreelancerProfileIntroSaveOrUpdateRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.MemberType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@ActiveProfiles("h2")
@SpringBootTest
class FreelancerProfileServiceTest {

    @Autowired
    private FreelancerProfileService freelancerProfileService;

    @Autowired
    private FreelancerRepository freelancerRepository;
    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;


    @DisplayName("프리랜서 소개정보가 저장 or 수정된다")
    @Test
    public void 프리랜서_소개정보_저장or수정() {
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

        FreelancerProfileIntroSaveOrUpdateRequest freelancerProfileIntroSaveOrUpdateRequest =
                new FreelancerProfileIntroSaveOrUpdateRequest("testname", IntroBackGround.COBALT_BLUE, "url", "introContent");
        MemberDetails memberDetails = new MemberDetails(memberId);

        //when
        freelancerProfileService.saveOrUpdateFreelancerProfileIntro(memberDetails, freelancer.getNum(), freelancerProfileIntroSaveOrUpdateRequest);

        //then
        FreelancerProfile updatedFreelancerProfile = freelancerProfileRepository.findById(freelancer.getNum()).get();
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceName()).isEqualTo(freelancerProfileIntroSaveOrUpdateRequest.getIntroName());
        Assertions.assertThat(updatedFreelancerProfile.getIntroBackGround()).isEqualTo(freelancerProfileIntroSaveOrUpdateRequest.getIntroBackGround());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceVideoURL()).isEqualTo(freelancerProfileIntroSaveOrUpdateRequest.getIntroVideoUrl());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceContent()).isEqualTo(freelancerProfileIntroSaveOrUpdateRequest.getIntroContent());
    }
}