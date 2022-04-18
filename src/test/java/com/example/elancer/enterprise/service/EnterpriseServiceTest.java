package com.example.elancer.enterprise.service;

import com.example.elancer.enterprise.domain.enterprise.Address;
import com.example.elancer.enterprise.domain.enterprise.CountryType;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.dto.EnterpriseIntroRequest;
import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
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

    @Test
    @DisplayName("기업 회원 가입")
    public void enterpriseJoin() {

        //given
        EnterpriseJoinRequest enterpriseJoinRequest = new EnterpriseJoinRequest(
                "testid",
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
        );

        //when
        enterpriseService.join(enterpriseJoinRequest);


        //then
        Enterprise joinEnterprise = enterpriseRepository.findByUserId(enterpriseJoinRequest.getUserId()).orElseThrow(NotExistEnterpriseException::new);
        assertThat(joinEnterprise.getUserId()).isEqualTo(enterpriseJoinRequest.getUserId());
        assertThat(joinEnterprise.getName()).isEqualTo(enterpriseJoinRequest.getName());
        assertThat(joinEnterprise.getPhone()).isEqualTo(enterpriseJoinRequest.getPhone());
        assertThat(joinEnterprise.getEmail()).isEqualTo(enterpriseJoinRequest.getEmail());
        assertThat(joinEnterprise.getCompanyName()).isEqualTo(enterpriseJoinRequest.getCompanyName());
        assertThat(joinEnterprise.getCompanyPeople()).isEqualTo(enterpriseJoinRequest.getCompanyPeople());
        assertThat(joinEnterprise.getPosition()).isEqualTo(enterpriseJoinRequest.getPosition());
        assertThat(joinEnterprise.getTelNumber()).isEqualTo(enterpriseJoinRequest.getTelNumber());
        assertThat(joinEnterprise.getWebsite()).isEqualTo(enterpriseJoinRequest.getWebsite());
        assertThat(joinEnterprise.getAddress().getCountry()).isEqualTo(enterpriseJoinRequest.getAddress().getCountry());
        assertThat(joinEnterprise.getBizContents()).isEqualTo(enterpriseJoinRequest.getBizContents());
        assertThat(joinEnterprise.getSales()).isEqualTo(enterpriseJoinRequest.getSales());
        assertThat(joinEnterprise.getIdNumber()).isEqualTo(enterpriseJoinRequest.getIdNumber());

    }


    @Test
    @DisplayName("기업 회원 가입 시, 중복된 아이디일 경우 예외 발생")
    public void enterpriseJoinDuplicateException() {

        //given
        EnterpriseJoinRequest enterpriseJoinRequest1 = new EnterpriseJoinRequest(
                "testid",
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
        );
        EnterpriseJoinRequest enterpriseJoinRequest2 = new EnterpriseJoinRequest(
                "testid",
                "1234",
                "1234",
                "name2",
                "01000000000",
                "test@gmail.com",
                "test company2",
                10,
                "사장2",
                "01011111111",
                "www.test.com",
                new Address(CountryType.KR, "123", "주소1", "주소2"),
                "주요 사업2",
                10000000,
                "사업자 번호(123-123-123)2"
        );

        //when
        enterpriseService.join(enterpriseJoinRequest1);


        //then
        Assertions.assertThrows(EnterpriseCheckUserIdException.class, () ->{
            enterpriseService.join(enterpriseJoinRequest2);
        });
    }

    @Test
    @DisplayName("회원 가입 시 비밀번호 일치하지 않으면 예외 발생")
    @Rollback(value = false)
    public void enterpriseJoinPwdException() {

        //given
        EnterpriseJoinRequest enterpriseJoinRequest = new EnterpriseJoinRequest(
                "testid",
                "1234",
                "12344",
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
        );

        //when, then
        Assertions.assertThrows(CheckPasswordException.class, () ->{
            enterpriseService.join(enterpriseJoinRequest);
        });
    }


    @Test
    @DisplayName("프로필 작성")
    public void enterpriseIntroUpdate() {

        //given
        enterpriseService.join(new EnterpriseJoinRequest(
                "testid",
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

        Long num = enterpriseRepository.findByUserId("testid").get().getNum();

        List<String> mainBiz = new ArrayList<>();
        mainBiz.add("main_biz1");
        mainBiz.add("main_biz2");

        List<String> subBiz = new ArrayList<>();
        subBiz.add("sub_biz1");
        subBiz.add("sub_biz2");

        EnterpriseIntroRequest enterpriseIntroRequest = new EnterpriseIntroRequest("타이틀", mainBiz, subBiz);


        //when
        enterpriseService.updateIntro(num, enterpriseIntroRequest, null);


        //then
        Enterprise result = enterpriseRepository.findById(num).orElseThrow(NotExistEnterpriseException::new);
        assertThat(result.getEnterpriseIntro().getIntroTitle()).isEqualTo(enterpriseIntroRequest.getIntroTitle());
        assertThat(result.getEnterpriseIntro().getEnterpriseMainBizs().size()).isEqualTo(2);
        assertThat(result.getEnterpriseIntro().getEnterpriseSubBizs().size()).isEqualTo(2);


    }



}