package com.example.elancer.freelancer.service;

import com.example.elancer.freelancer.dto.FreelancerIntroSaveOrUpdateRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.MemberType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@SpringBootTest
class FreelancerServiceTest {
    @Autowired
    private FreelancerService freelancerService;

    @Autowired
    private FreelancerRepository freelancerRepository;

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

        FreelancerIntroSaveOrUpdateRequest freelancerIntroSaveOrUpdateRequest =
                new FreelancerIntroSaveOrUpdateRequest("testname", IntroBackGround.COBALT_BLUE, "url", "introContent");
        MemberDetails memberDetails = new MemberDetails(memberId);

        //when
        freelancerService.saveOrUpdateFreelancerIntro(memberDetails, freelancer.getNum(), freelancerIntroSaveOrUpdateRequest);

        //then
        Freelancer updatedFreelancer = freelancerRepository.findById(freelancer.getNum()).get();
//        Assertions.assertThat(updatedFreelancer.getIntroduceName()).isEqualTo(freelancerIntroSaveOrUpdateRequest.getIntroName());
//        Assertions.assertThat(updatedFreelancer.getIntroBackGround()).isEqualTo(freelancerIntroSaveOrUpdateRequest.getIntroBackGround());
//        Assertions.assertThat(updatedFreelancer.getIntroduceVideoURL()).isEqualTo(freelancerIntroSaveOrUpdateRequest.getIntroVideoUrl());
//        Assertions.assertThat(updatedFreelancer.getIntroduceContent()).isEqualTo(freelancerIntroSaveOrUpdateRequest.getIntroContent());

    }

}