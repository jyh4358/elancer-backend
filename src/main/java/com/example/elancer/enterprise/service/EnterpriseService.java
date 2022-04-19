package com.example.elancer.enterprise.service;

import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.domain.enterpriseintro.*;
import com.example.elancer.enterprise.dto.EnterpriseIntroRequest;
import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
import com.example.elancer.enterprise.exception.EnterpriseCheckUserIdException;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.enterprise.repository.MainBusinessRepository;
import com.example.elancer.enterprise.repository.SubBusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnterpriseService {

    private final PasswordEncoder passwordEncoder;
    private final EnterpriseRepository enterpriseRepository;
    private final MainBusinessRepository mainBusinessRepository;
    private final SubBusinessRepository subBusinessRepository;

    @Transactional
    public void join(EnterpriseJoinRequest enterpriseJoinRequest) {
        enterpriseJoinRequest.checkPwd();
        enterpriseJoinRequest.setPassword1(passwordEncoder.encode(enterpriseJoinRequest.getPassword1()));
        Enterprise enterprise = enterpriseJoinRequest.toEntity();
        checkDuplicate(enterprise.getUserId());

        enterpriseRepository.save(enterprise);
    }



    @Transactional
    public void updateIntro(String userId, EnterpriseIntroRequest enterpriseIntroRequest, String etc) {

        Enterprise enterprise = enterpriseRepository.findByUserId(userId).orElseThrow(NotExistEnterpriseException::new);

        List<EnterpriseMainBiz> enterpriseMainBizs = getEnterpriseMainBizs(enterpriseIntroRequest, etc);
        List<EnterpriseSubBiz> enterpriseSubBizs = getEnterpriseSubBizs(enterpriseIntroRequest, etc);

        EnterpriseIntro enterpriseIntro = EnterpriseIntro.of(enterpriseIntroRequest.getIntroTitle(), enterpriseMainBizs, enterpriseSubBizs, enterprise);

        enterprise.updateIntro(enterpriseIntro);

    }


    private void checkDuplicate(String userId) {
        if (enterpriseRepository.existsByUserId(userId)) {
            throw new EnterpriseCheckUserIdException();
        }
    }
    private List<EnterpriseSubBiz> getEnterpriseSubBizs(EnterpriseIntroRequest enterpriseIntroRequest, String etc) {
        List<SubBusiness> subBusiness = subBusinessRepository.findSubBusiness(enterpriseIntroRequest.getSubBizCodes());
        return EnterpriseSubBiz.createList(subBusiness, etc);
    }

    private List<EnterpriseMainBiz> getEnterpriseMainBizs(EnterpriseIntroRequest enterpriseIntroRequest, String etc) {

        List<MainBusiness> mainBusiness = mainBusinessRepository.findMainBusiness(enterpriseIntroRequest.getMainBizCodes());
        return EnterpriseMainBiz.createList(mainBusiness, etc);
    }

}
