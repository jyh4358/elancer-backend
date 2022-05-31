package com.example.elancer.wishfreelancer.service;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishfreelancer.model.WishFreelancer;
import com.example.elancer.wishfreelancer.repository.WishFreelancerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
@SpringBootTest
class WishFreelancerServiceTest extends ServiceBaseTest {

    @Autowired
    private WishFreelancerRepository wishFreelancerRepository;

    @Autowired
    private WishFreelancerService wishFreelancerService;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }


    @DisplayName("프리랜서 스크랩 추가")
    @Test
    public void 프리랜서_스크랩_추가() {

        // given
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);

        MemberDetails memberDetails = new MemberDetails(enterprise.getNum(), enterprise.getUserId(), enterprise.getRole());

        // when
        wishFreelancerService.addWishFreelancer(memberDetails, freelancer.getNum());

        // then
        WishFreelancer wishFreelancer = wishFreelancerRepository.findAll().get(0);

        Assertions.assertThat(wishFreelancer.getEnterprise().getNum()).isEqualTo(memberDetails.getId());
        Assertions.assertThat(wishFreelancer.getFreelancer().getNum()).isEqualTo(freelancer.getNum());
    }

}