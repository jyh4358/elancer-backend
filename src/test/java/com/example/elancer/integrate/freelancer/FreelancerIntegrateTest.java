package com.example.elancer.integrate.freelancer;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.controller.FreelancerControllerPath;
import com.example.elancer.freelancer.controller.FreelancerEnumControllerPath;
import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
import com.example.elancer.freelancer.join.controller.FreelancerJoinControllerPath;
import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerWorkType;
import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancer.model.KOSAState;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.PresentWorkState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.model.WorkType;
import com.example.elancer.freelancer.repository.FreelancerWorkTypeRepository;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerIntegrateTest extends IntegrateBaseTest {

    @Autowired
    private FreelancerWorkTypeRepository freelancerWorkTypeRepository;

    @DisplayName("프리랜서 회원가입 통합테스트")
    @Test
    public void 프래랜서_회원가입_통합테스트() throws Exception {
        //given
        FreelancerJoinRequest freelancerJoinRequest = new FreelancerJoinRequest(
                "name",
                "memberId",
                "pwd",
                "pwd",
                "email@manver.com",
                MailReceptionState.RECEPTION,
                "phone",
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2021, 02, 01),
                null
        );

        //when
        mockMvc.perform(post(FreelancerJoinControllerPath.FREELANCER_JOIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(freelancerJoinRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        List<Freelancer> freelancers = freelancerRepository.findAll();
        Assertions.assertThat(freelancers).hasSize(1);
        Assertions.assertThat(freelancers.get(0).getUserId()).isEqualTo(freelancerJoinRequest.getMemberId());
        Assertions.assertThat(freelancers.get(0).getName()).isEqualTo(freelancerJoinRequest.getMemberName());
        Assertions.assertThat(freelancers.get(0).getEmail()).isEqualTo(freelancerJoinRequest.getMemberEmail());
        Assertions.assertThat(freelancers.get(0).getPhone()).isEqualTo(freelancerJoinRequest.getMemberPhone());
        Assertions.assertThat(freelancers.get(0).getFreelancerAccountInfo().getMailReceptionState()).isEqualTo(freelancerJoinRequest.getMailReceptionState());
        Assertions.assertThat(freelancers.get(0).getFreelancerAccountInfo().getWorkPossibleState()).isEqualTo(freelancerJoinRequest.getWorkPossibleState());
        Assertions.assertThat(freelancers.get(0).getFreelancerAccountInfo().getWorkStartPossibleDate()).isEqualTo(freelancerJoinRequest.getWorkStartPossibleDate());
    }

    @DisplayName("프리랜서 계정 정보 수정 통합테스트")
    //TODO 임시 주석 jwt 구현후 진행해야 한다.
//    @Test
    public void 프래랜서_계정정보_수정_통합테스트() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository);

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
        mockMvc.perform(put(FreelancerControllerPath.FREELANCER_ACCOUNT_INFO_UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(freelancerAccountCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        List<Freelancer> freelancers = freelancerRepository.findAll();
        Assertions.assertThat(freelancers).hasSize(1);
        Assertions.assertThat(freelancers.get(0).getName()).isEqualTo(freelancerAccountCoverRequest.getName());
    }

    @DisplayName("프리랜서 계정 정보 조회 통합테스트")
    @Test
    public void 프래랜서_계정정보_조회_통합테스트() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository);

        List<WorkType> workTypes = freelancerWorkTypeRepository.saveAll(Arrays.asList(WorkType.createWorkType(FreelancerWorkType.ACCOUNTING, freelancer), WorkType.createWorkType(FreelancerWorkType.BIGDATA, freelancer)));

        MemberDetails memberDetails = new MemberDetails(freelancer.getUserId());
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

        Freelancer updatedFreelancer = freelancerRepository.save(freelancer);

        //when & then
        String path = FreelancerControllerPath.FREELANCER_ACCOUNT_INFO_FIND.replace("{freelancerNum}", String.valueOf(updatedFreelancer.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(updatedFreelancer.getName()))
                .andDo(print());
    }

    @DisplayName("프리랜서 국가정보 조회 통합테스트")
    @Test
    public void 프래랜서_국가정보_조회_통합테스트() throws Exception {
        //when & then
        mockMvc.perform(get(FreelancerEnumControllerPath.FREELANCER_COUNTRIES_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("countryNames.[0]").value("대한민국"))
                .andDo(print());
    }

    @DisplayName("프리랜서 업무분야 조회 통합테스트")
    @Test
    public void 프래랜서_업무분야_조회_통합테스트() throws Exception {
        //when & then
        mockMvc.perform(get(FreelancerEnumControllerPath.FREELANCER_WORK_TYPE_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("workTypeNames.[0]").value("쇼핑몰"))
                .andDo(print());
    }

    @AfterEach
    void tearDown() {
        databaseClean.clean();
    }
}
