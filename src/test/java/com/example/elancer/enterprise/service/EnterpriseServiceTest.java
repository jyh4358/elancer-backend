package com.example.elancer.enterprise.service;

import com.example.elancer.enterprise.domain.Address;
import com.example.elancer.enterprise.domain.CountryType;
import com.example.elancer.enterprise.domain.Enterprise;
import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EnterpriseServiceTest {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Test
    @DisplayName("Enterprise join")
    @Rollback(value = false)
    public void enterpriseJoin() {

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

        enterpriseService.join(enterpriseJoinRequest);


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

}