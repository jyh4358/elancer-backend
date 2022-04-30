package com.example.elancer.freelancer.service;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
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
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.CountryType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("h2")
@SpringBootTest
class FreelancerServiceTest {
    @Autowired
    private FreelancerService freelancerService;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerWorkTypeRepository freelancerWorkTypeRepository;

    @DisplayName("프리랜서 계정 정보가 업데이트 된다")
    @Test
    public void 프리랜서_계정_정보가_업데이트() {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository);

        MemberDetails memberDetails = new MemberDetails(freelancer.getUserId());

        FreelancerAccountCoverRequest freelancerAccountCoverRequest = new FreelancerAccountCoverRequest(
                "멤버이름",
                "패스워드",
                "패스워드",
                LocalDate.of(2000, 01, 01),
                "email@email.email.com",
                "010-0101-0101",
                "http://web.com",
                CountryType.KR,
                "경기도",
                "성남시",
                "중원구",
                Arrays.asList(FreelancerWorkType.ACCOUNTING, FreelancerWorkType.BIGDATA),
                null,
                null,
                9,
                5,
                400,
                600,
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
        freelancerService.coverFreelancerAccountInfo(freelancer.getNum(), memberDetails, freelancerAccountCoverRequest);

        //then
        Freelancer updatedFreelancer = freelancerRepository.findById(freelancer.getNum()).get();
        Assertions.assertThat(updatedFreelancer.getName()).isEqualTo(freelancerAccountCoverRequest.getName());
        Assertions.assertThat(updatedFreelancer.getBirthDate()).isEqualTo(freelancerAccountCoverRequest.getBirthDate());
        Assertions.assertThat(updatedFreelancer.getEmail()).isEqualTo(freelancerAccountCoverRequest.getEmail());
        Assertions.assertThat(updatedFreelancer.getPhone()).isEqualTo(freelancerAccountCoverRequest.getPhone());
        Assertions.assertThat(updatedFreelancer.getWebsite()).isEqualTo(freelancerAccountCoverRequest.getWebsite());
        Assertions.assertThat(updatedFreelancer.getAddress().getCountry()).isEqualTo(freelancerAccountCoverRequest.getCountryType());
        Assertions.assertThat(updatedFreelancer.getAddress().getZipcode()).isEqualTo(freelancerAccountCoverRequest.getZipcode());
        Assertions.assertThat(updatedFreelancer.getAddress().getMainAddress()).isEqualTo(freelancerAccountCoverRequest.getMainAddress());
        Assertions.assertThat(updatedFreelancer.getAddress().getDetailAddress()).isEqualTo(freelancerAccountCoverRequest.getDetailAddress());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getWorkEtcField()).isEqualTo(freelancerAccountCoverRequest.getWorkEtcField());
//        Assertions.assertThat(updatedFreelancer.getCareerForm()).isEqualTo(freelancerAccountCoverRequest.getCareerForm());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getCareerYear()).isEqualTo(freelancerAccountCoverRequest.getCareerYear());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getCareerMonth()).isEqualTo(freelancerAccountCoverRequest.getCareerMonth());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeMonthMinPay()).isEqualTo(freelancerAccountCoverRequest.getHopeMonthMinPay());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeMonthMaxPay()).isEqualTo(freelancerAccountCoverRequest.getHopeMonthMaxPay());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getKosaState()).isEqualTo(freelancerAccountCoverRequest.getKosaState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getMailReceptionState()).isEqualTo(freelancerAccountCoverRequest.getMailReceptionState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getPresentWorkState()).isEqualTo(freelancerAccountCoverRequest.getPresentWorkState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeWorkState()).isEqualTo(freelancerAccountCoverRequest.getHopeWorkState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getWorkPossibleState()).isEqualTo(freelancerAccountCoverRequest.getWorkPossibleState());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getWorkStartPossibleDate()).isEqualTo(freelancerAccountCoverRequest.getWorkStartPossibleDate());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeWorkCountry()).isEqualTo(freelancerAccountCoverRequest.getHopeWorkCountry());
        Assertions.assertThat(updatedFreelancer.getFreelancerAccountInfo().getHopeWorkCity()).isEqualTo(freelancerAccountCoverRequest.getHopeWorkCity());

        List<WorkType> workTypes = freelancerWorkTypeRepository.findAll();
        Assertions.assertThat(workTypes).hasSize(2);
        Assertions.assertThat(workTypes.get(0).getFreelancerWorkType()).isEqualTo(freelancerAccountCoverRequest.getFreelancerWorkTypes().get(0));
        Assertions.assertThat(workTypes.get(1).getFreelancerWorkType()).isEqualTo(freelancerAccountCoverRequest.getFreelancerWorkTypes().get(1));
    }


}