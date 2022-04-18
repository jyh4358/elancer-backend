package com.example.elancer.integrate.freelancerprofile;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.member.domain.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

public class FreelancerProfileIntegrateTest extends IntegrateBaseTest {

    @Autowired
    private FreelancerRepository freelancerRepository;

//    @Autowired
//    private Login

    @DisplayName("프리랜서 프로필 소개정보 저장 테스트")
    @Test
    public void 프리랜서_프로필_소개정보_저장() {
        //given

        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository);
        //when
        //then
    }
}
