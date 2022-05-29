package com.example.elancer.enterprise.service;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.enterprise.dto.EnterpriseAccountDetailResponse;
import com.example.elancer.enterprise.dto.EnterpriseUpdateRequest;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;



class EnterpriseServiceTest extends ServiceBaseTest {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @AfterEach
    void tearDown() {
        databaseClean.clean();
    }


    @Test
    @DisplayName("기업 게정 정보가 업데이트 된다.")
    public void 기업_계정_정보_업데이트() {

        // given
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);

        MemberDetails memberDetails = MemberDetails.builder()
                .id(enterprise.getNum())
                .userId(enterprise.getUserId())
                .role(enterprise.getRole())
                .build();

        EnterpriseUpdateRequest enterpriseUpdateRequest = new EnterpriseUpdateRequest(
                "변경된 회사 이름",
                20,
                "변경된 이름",
                "부장",
                "12345",
                "12345",
                "01033333333",
                "01044444444",
                "changedEmail@gmail.com",
                "www.changedWebsite.com",
                new Address(CountryType.CN, "경기도", "주소1", "주소2"),
                "쇼핑몰",
                200000000,
                "111-111-111"
        );

        // when
        enterpriseService.coverEnterpriseAccountInfo(memberDetails, enterpriseUpdateRequest);


        // then
        Enterprise findEnterprise = enterpriseRepository.findById(enterprise.getNum()).get();
        Assertions.assertThat(findEnterprise.getCompanyName()).isEqualTo(enterpriseUpdateRequest.getCompanyName());
        Assertions.assertThat(findEnterprise.getCompanyPeople()).isEqualTo(enterpriseUpdateRequest.getCompanyPeople());
        Assertions.assertThat(findEnterprise.getName()).isEqualTo(enterpriseUpdateRequest.getName());
        Assertions.assertThat(findEnterprise.getPosition()).isEqualTo(enterpriseUpdateRequest.getPosition());
        Assertions.assertThat(findEnterprise.getPhone()).isEqualTo(enterpriseUpdateRequest.getPhone());
        Assertions.assertThat(findEnterprise.getTelNumber()).isEqualTo(enterpriseUpdateRequest.getTelNumber());
        Assertions.assertThat(findEnterprise.getEmail()).isEqualTo(enterpriseUpdateRequest.getEmail());
        Assertions.assertThat(findEnterprise.getWebsite()).isEqualTo(enterpriseUpdateRequest.getWebsite());
        Assertions.assertThat(findEnterprise.getAddress().getCountry()).isEqualTo(enterpriseUpdateRequest.getAddress().getCountry());
        Assertions.assertThat(findEnterprise.getAddress().getZipcode()).isEqualTo(enterpriseUpdateRequest.getAddress().getZipcode());
        Assertions.assertThat(findEnterprise.getAddress().getMainAddress()).isEqualTo(enterpriseUpdateRequest.getAddress().getMainAddress());
        Assertions.assertThat(findEnterprise.getAddress().getDetailAddress()).isEqualTo(enterpriseUpdateRequest.getAddress().getDetailAddress());
        Assertions.assertThat(findEnterprise.getBizContents()).isEqualTo(enterpriseUpdateRequest.getBizContents());
        Assertions.assertThat(findEnterprise.getSales()).isEqualTo(enterpriseUpdateRequest.getSales());
        Assertions.assertThat(findEnterprise.getIdNumber()).isEqualTo(enterpriseUpdateRequest.getIdNumber());

    }

    @Test
    @DisplayName("기업 계정 정보를 조회한다.")
    public void 기업_계정_정보_조회() {

        // given
        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository, passwordEncoder);

        MemberDetails memberDetails = new MemberDetails(
                enterprise.getNum(),
                enterprise.getUserId(),
                enterprise.getRole());

        // when
        EnterpriseAccountDetailResponse enterpriseAccountInfo = enterpriseService.findDetailEnterpriseAccount(memberDetails);

        // then
        Assertions.assertThat(enterpriseAccountInfo.getCompanyName()).isEqualTo(enterprise.getCompanyName());
        Assertions.assertThat(enterpriseAccountInfo.getCompanyPeople()).isEqualTo(enterprise.getCompanyPeople());
        Assertions.assertThat(enterpriseAccountInfo.getName()).isEqualTo(enterprise.getName());
        Assertions.assertThat(enterpriseAccountInfo.getPosition()).isEqualTo(enterprise.getPosition());
        Assertions.assertThat(enterpriseAccountInfo.getPhone()).isEqualTo(enterprise.getPhone());
        Assertions.assertThat(enterpriseAccountInfo.getTelNumber()).isEqualTo(enterprise.getTelNumber());
        Assertions.assertThat(enterpriseAccountInfo.getEmail()).isEqualTo(enterprise.getEmail());
        Assertions.assertThat(enterpriseAccountInfo.getWebsite()).isEqualTo(enterprise.getWebsite());
        Assertions.assertThat(enterpriseAccountInfo.getAddress().getCountry()).isEqualTo(enterprise.getAddress().getCountry());
        Assertions.assertThat(enterpriseAccountInfo.getAddress().getZipcode()).isEqualTo(enterprise.getAddress().getZipcode());
        Assertions.assertThat(enterpriseAccountInfo.getAddress().getMainAddress()).isEqualTo(enterprise.getAddress().getMainAddress());
        Assertions.assertThat(enterpriseAccountInfo.getAddress().getDetailAddress()).isEqualTo(enterprise.getAddress().getDetailAddress());
        Assertions.assertThat(enterpriseAccountInfo.getBizContents()).isEqualTo(enterprise.getBizContents());
        Assertions.assertThat(enterpriseAccountInfo.getSales()).isEqualTo(enterprise.getSales());
        Assertions.assertThat(enterpriseAccountInfo.getIdNumber()).isEqualTo(enterprise.getIdNumber());


    }


//    @Test
//    @DisplayName("기업 게정 프로파일 정보를 조회한다.")
//    public void enterpriseIntroUpdate() {
//
//        //given
//        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository);
//
//        List<String> mainBiz = new ArrayList<>();
//        mainBiz.add("main_biz1");
//        mainBiz.add("main_biz2");
//
//        List<String> subBiz = new ArrayList<>();
//        subBiz.add("sub_biz1");
//        subBiz.add("sub_biz2");
//
//        EnterpriseProfileRequest enterpriseIntroRequest = new EnterpriseProfileRequest("타이틀", "SI", 1000, "123-123-123",  mainBiz, subBiz);
//
//
//        //when
//        enterpriseService.updateIntro(memberNum, enterpriseIntroRequest, null);
//
//
//        //then
//        Enterprise result = enterpriseRepository.findById(memberNum).get();
//        assertThat(result.getEnterpriseIntro().getIntroTitle()).isEqualTo(enterpriseIntroRequest.getIntroTitle());
//        assertThat(result.getEnterpriseIntro().getEnterpriseMainBizs().size()).isEqualTo(2);
//        assertThat(result.getEnterpriseIntro().getEnterpriseSubBizs().size()).isEqualTo(2);
//
//
//    }



}