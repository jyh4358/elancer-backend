package com.example.elancer.enterprise.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.domain.enterpriseintro.*;
import com.example.elancer.enterprise.dto.EnterpriseAccountDetailResponse;
import com.example.elancer.enterprise.dto.EnterpriseIntroRequest;
import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
import com.example.elancer.enterprise.dto.EnterpriseUpdateRequest;
import com.example.elancer.enterprise.exception.EnterpriseCheckUserIdException;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.enterprise.repository.MainBusinessRepository;
import com.example.elancer.enterprise.repository.SubBusinessRepository;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnterpriseService {

    private final PasswordEncoder passwordEncoder;
    private final EnterpriseRepository enterpriseRepository;
    private final MainBusinessRepository mainBusinessRepository;
    private final SubBusinessRepository subBusinessRepository;



    @Transactional
    public void coverEnterpriseAccountInfo(MemberDetails memberDetails, EnterpriseUpdateRequest enterpriseUpdateRequest) {

        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(EnterpriseCheckUserIdException::new);
        RightRequestChecker.checkPasswordMatch(enterpriseUpdateRequest.getPassword1(), enterpriseUpdateRequest.getPassword2());

        enterprise.updateEnterprise(
                passwordEncoder.encode(enterpriseUpdateRequest.getPassword1()),
                enterpriseUpdateRequest.getName(),
                enterpriseUpdateRequest.getPhone(),
                enterpriseUpdateRequest.getEmail(),
                enterpriseUpdateRequest.getCompanyName(),
                enterpriseUpdateRequest.getCompanyPeople(),
                enterpriseUpdateRequest.getPosition(),
                enterpriseUpdateRequest.getTelNumber(),
                enterpriseUpdateRequest.getWebsite(),
                enterpriseUpdateRequest.getAddress(),
                enterpriseUpdateRequest.getBizContents(),
                enterpriseUpdateRequest.getSales(),
                enterpriseUpdateRequest.getIdNumber()
        );
    }

    public EnterpriseAccountDetailResponse findDetailEnterpriseAccount(Long num) {
        Enterprise enterprise = enterpriseRepository.findById(num).orElseThrow(NotExistEnterpriseException::new);
        return EnterpriseAccountDetailResponse.of(enterprise);
    }


    @Transactional
    public void enterpriseUpdate(String userId, EnterpriseJoinRequest enterpriseJoinRequest) {
//        enterpriseRepository.findByUserId(userId);

    }



    @Transactional
    public void updateIntro(MemberDetails memberDetails, EnterpriseIntroRequest enterpriseIntroRequest) {

        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);

        List<EnterpriseMainBiz> enterpriseMainBizs = getEnterpriseMainBizs(enterpriseIntroRequest);
        List<EnterpriseSubBiz> enterpriseSubBizs = getEnterpriseSubBizs(enterpriseIntroRequest);


        EnterpriseIntro enterpriseIntro = EnterpriseIntro.of(enterpriseIntroRequest.getIntroTitle(), enterpriseMainBizs, enterpriseSubBizs, enterprise);


        enterprise.updateIntro(enterpriseIntro,
                enterpriseIntroRequest.getBizContents(),
                enterpriseIntroRequest.getSales(),
                enterpriseIntroRequest.getIdNumber());

    }

    public void findScrap(Long id) {
        Enterprise enterprise = enterpriseRepository.findScrapAndFreelancerFetchJoin(id).orElseThrow(NotExistEnterpriseException::new);
        List<Freelancer> collect = enterprise.getHeartScraps().stream().map(scrap -> scrap.getFreelancer()).collect(Collectors.toList());
        // todo - entityy -> dto 변환 로직 후 반환

    }




    private void checkDuplicate(String userId) {
        if (enterpriseRepository.existsByUserId(userId)) {
            throw new EnterpriseCheckUserIdException();
        }
    }
    private List<EnterpriseSubBiz> getEnterpriseSubBizs(EnterpriseIntroRequest enterpriseIntroRequest) {
        List<SubBusiness> subBusiness = subBusinessRepository.findSubBusiness(enterpriseIntroRequest.getSubBizCodes());
        return EnterpriseSubBiz.createList(subBusiness);
    }

    private List<EnterpriseMainBiz> getEnterpriseMainBizs(EnterpriseIntroRequest enterpriseIntroRequest) {

        List<MainBusiness> mainBusiness = mainBusinessRepository.findMainBusiness(enterpriseIntroRequest.getMainBizCodes());
        return EnterpriseMainBiz.createList(mainBusiness);
    }

}
