package com.example.elancer.enterprise.join.service;

import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
import com.example.elancer.enterprise.exception.CheckPasswordException;
import com.example.elancer.enterprise.exception.EnterpriseCheckUserIdException;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


class EnterpriseJoinServiceTest extends ServiceBaseTest {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private EnterpriseJoinService enterpriseJoinService;

    @AfterEach
    void tearDown() {
        this.databaseClean.clean();
    }


    @DisplayName("기업 회원 가입")
    @Test
    public void 기업_회원가입() {

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
        enterpriseJoinService.joinEnterprise(enterpriseJoinRequest);


        //then
        Enterprise joinEnterprise = enterpriseRepository.findAll().get(0);
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
        assertThat(joinEnterprise.getAddress().getZipcode()).isEqualTo(enterpriseJoinRequest.getAddress().getZipcode());
        assertThat(joinEnterprise.getAddress().getMainAddress()).isEqualTo(enterpriseJoinRequest.getAddress().getMainAddress());
        assertThat(joinEnterprise.getAddress().getDetailAddress()).isEqualTo(enterpriseJoinRequest.getAddress().getDetailAddress());
        assertThat(joinEnterprise.getBizContents()).isEqualTo(enterpriseJoinRequest.getBizContents());
        assertThat(joinEnterprise.getSales()).isEqualTo(enterpriseJoinRequest.getSales());
        assertThat(joinEnterprise.getIdNumber()).isEqualTo(enterpriseJoinRequest.getIdNumber());

    }

    @Test
    @DisplayName("[예외] 기업 회원 가입 시, 중복된 아이디일 경우 예외 발생")
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
        enterpriseJoinService.joinEnterprise(enterpriseJoinRequest1);


        //then
        Assertions.assertThrows(EnterpriseCheckUserIdException.class, () ->{
            enterpriseJoinService.joinEnterprise(enterpriseJoinRequest2);
        });
    }

    @Test
    @DisplayName("[예외] 회원 가입 시 비밀번호 일치하지 않으면 예외 발생")
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
            enterpriseJoinService.joinEnterprise(enterpriseJoinRequest);
        });
    }


}