package com.example.elancer.enterprise.join.service;

import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.model.enterprise.EnterpriseBizRegistration;
import com.example.elancer.enterprise.model.enterprise.EnterpriseThumbnail;
import com.example.elancer.enterprise.model.enterpriseintro.EnterpriseIntro;
import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
import com.example.elancer.enterprise.exception.EnterpriseCheckUserIdException;
import com.example.elancer.enterprise.repository.EnterpriseBizRegistrationRepository;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.enterprise.repository.EnterpriseThumbnailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnterpriseJoinService {

    private final EnterpriseRepository enterpriseRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
//    private final EnterpriseProfileRepository enterpriseProfileRepository;
    private final EnterpriseThumbnailRepository enterpriseThumbnailRepository;
    private final EnterpriseBizRegistrationRepository enterpriseBizRegistrationRepository;

    @Transactional
    public void joinEnterprise(EnterpriseJoinRequest enterpriseJoinRequest) {
        enterpriseJoinRequest.checkPwd();
        checkDuplicate(enterpriseJoinRequest.getUserId());
        enterpriseJoinRequest.setPassword1(bCryptPasswordEncoder.encode(enterpriseJoinRequest.getPassword1()));
        Enterprise enterprise = enterpriseJoinRequest.toEntity();

        EnterpriseIntro enterpriseIntro = EnterpriseIntro.initializeFrom(enterprise.getName());
        enterprise.initialIntro(enterpriseIntro);

        Enterprise savedEnterprise = enterpriseRepository.save(enterprise);

        saveEnterpriseThumbnail(enterpriseJoinRequest, savedEnterprise);
        saveEnterpriseBizRegistration(enterpriseJoinRequest, savedEnterprise);

    }



    private void saveEnterpriseThumbnail(EnterpriseJoinRequest enterpriseJoinRequest, Enterprise savedEnterprise) {
        if (enterpriseJoinRequest.getThumbnail() == null) {
            return;
        }
        enterpriseThumbnailRepository.save(EnterpriseThumbnail.createEnterpriseThumbnail(enterpriseJoinRequest.getThumbnail(), savedEnterprise));
    }

    private void saveEnterpriseBizRegistration(EnterpriseJoinRequest enterpriseJoinRequest, Enterprise savedEnterprise) {
        if (enterpriseJoinRequest.getBizRegistrationFile() == null) {
            return;
        }
        enterpriseBizRegistrationRepository.save(EnterpriseBizRegistration.createEnterpriseBizRegistration(enterpriseJoinRequest.getBizRegistrationFile(), savedEnterprise));
    }


    private void checkDuplicate(String userId) {
        if (enterpriseRepository.existsByUserId(userId)) {
            throw new EnterpriseCheckUserIdException();
        }
    }

}
