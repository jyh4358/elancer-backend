package com.example.elancer.freelancer.join.service;

import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.join.exception.FreelancerCheckPasswordException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

class FreelancerJoinServiceTest extends ServiceBaseTest {
    @Autowired
    private FreelancerJoinService freelancerJoinService;


    @DisplayName("프리랜서 가입이 완료된다.")
//    @Test
    public void 프리랜서_가입() {
        //given
        FreelancerJoinRequest freelancerJoinRequest = new FreelancerJoinRequest(
                "name",
                "memberId",
                "pwd",
                "pwd",
                "email",
                MailReceptionState.RECEPTION,
                "phone",
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        );

        //when
        freelancerJoinService.joinFreelancer(freelancerJoinRequest);

        //then
        Freelancer joinedFreelancer = freelancerRepository.findAll().get(0);
        Assertions.assertThat(joinedFreelancer.getName()).isEqualTo(freelancerJoinRequest.getMemberName());
        Assertions.assertThat(joinedFreelancer.getUserId()).isEqualTo(freelancerJoinRequest.getMemberId());
        Assertions.assertThat(joinedFreelancer.getEmail()).isEqualTo(freelancerJoinRequest.getMemberEmail());
        Assertions.assertThat(joinedFreelancer.getFreelancerAccountInfo().getMailReceptionState()).isEqualTo(freelancerJoinRequest.getMailReceptionState());
        Assertions.assertThat(joinedFreelancer.getPhone()).isEqualTo(freelancerJoinRequest.getMemberPhone());
        Assertions.assertThat(joinedFreelancer.getFreelancerAccountInfo().getWorkStartPossibleDate()).isEqualTo(freelancerJoinRequest.getWorkStartPossibleDate());
        Assertions.assertThat(joinedFreelancer.getFreelancerAccountInfo().getWorkStartPossibleDate()).isEqualTo(freelancerJoinRequest.getWorkStartPossibleDate());

        List<FreelancerProfile> freelancerProfiles = freelancerProfileRepository.findAll();
        Assertions.assertThat(freelancerProfiles).hasSize(1);

    }

    @DisplayName("[예외] 프리랜서 가입중 비밀번호와 비밀번호 확인내용이 다른경우 예외처리")
    @Test
    public void 프리랜서_가입_예외() {
        //given
        FreelancerJoinRequest freelancerJoinRequest = new FreelancerJoinRequest(
                "name",
                "memberId",
                "pwd",
                "pwd22",
                "email",
                MailReceptionState.RECEPTION,
                "phone",
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        );

        //when & then
        Assertions.assertThatThrownBy(() -> freelancerJoinService.joinFreelancer(freelancerJoinRequest)).isInstanceOf(FreelancerCheckPasswordException.class);
    }

    @AfterEach
    void tearDown() {
        this.databaseClean.clean();
    }
}