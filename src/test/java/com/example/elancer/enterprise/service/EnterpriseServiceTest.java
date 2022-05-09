package com.example.elancer.enterprise.service;

import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.dto.EnterpriseIntroRequest;
import com.example.elancer.enterprise.exception.CheckPasswordException;
import com.example.elancer.enterprise.exception.EnterpriseCheckUserIdException;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class EnterpriseServiceTest {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EnterpriseRepository enterpriseRepository;





//    @Test
    @DisplayName("프로필 작성")
    public void enterpriseIntroUpdate() {

        String userId = "testid";

        //given
        Long memberNum = enterpriseService.join(new EnterpriseJoinRequest(
                userId,
                "1234",
                "1234",
                "name",
                "01000000000",
                "test@gmail.com",
                "test company",
                10,
                "사장",
                "01011111111",
                "www.test.com",
                new Address(CountryType.KR, "123", "주소1", "주소2"),
                "주요 사업",
                10000000,
                "사업자 번호(123-123-123)"
        ));

        Enterprise enterprise = enterpriseRepository.findById(memberNum).get();

        List<String> mainBiz = new ArrayList<>();
        mainBiz.add("main_biz1");
        mainBiz.add("main_biz2");

        List<String> subBiz = new ArrayList<>();
        subBiz.add("sub_biz1");
        subBiz.add("sub_biz2");

        EnterpriseIntroRequest enterpriseIntroRequest = new EnterpriseIntroRequest("타이틀", "SI", 1000, "123-123-123",  mainBiz, subBiz);


        //when
        enterpriseService.updateIntro(memberNum, enterpriseIntroRequest, null);


        //then
        Enterprise result = enterpriseRepository.findById(memberNum).get();
        assertThat(result.getEnterpriseIntro().getIntroTitle()).isEqualTo(enterpriseIntroRequest.getIntroTitle());
        assertThat(result.getEnterpriseIntro().getEnterpriseMainBizs().size()).isEqualTo(2);
        assertThat(result.getEnterpriseIntro().getEnterpriseSubBizs().size()).isEqualTo(2);


    }



}