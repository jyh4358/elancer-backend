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
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.login.auth.dto.MemberDetails;
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
    public Long join(EnterpriseJoinRequest enterpriseJoinRequest) {

        enterpriseJoinRequest.checkPwd();
        checkDuplicate(enterpriseJoinRequest.getUserId());
        enterpriseJoinRequest.setPassword1(passwordEncoder.encode(enterpriseJoinRequest.getPassword1()));
        Enterprise enterprise = enterpriseJoinRequest.toEntity();

        enterpriseRepository.save(enterprise);
        return enterprise.getNum();
    }

    public void coverEnterpriseAccountInfo(MemberDetails memberDetails, EnterpriseJoinRequest enterpriseJoinRequest) {


    }


    @Transactional
    public void enterpriseUpdate(String userId, EnterpriseJoinRequest enterpriseJoinRequest) {
//        enterpriseRepository.findByUserId(userId);

    }



    @Transactional
    public void updateIntro(Long id, EnterpriseIntroRequest enterpriseIntroRequest, String etc) {

        Enterprise enterprise = enterpriseRepository.findById(id).orElseThrow(NotExistEnterpriseException::new);

        List<EnterpriseMainBiz> enterpriseMainBizs = getEnterpriseMainBizs(enterpriseIntroRequest, etc);
        List<EnterpriseSubBiz> enterpriseSubBizs = getEnterpriseSubBizs(enterpriseIntroRequest, etc);


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
    private List<EnterpriseSubBiz> getEnterpriseSubBizs(EnterpriseIntroRequest enterpriseIntroRequest, String etc) {
        List<SubBusiness> subBusiness = subBusinessRepository.findSubBusiness(enterpriseIntroRequest.getSubBizCodes());
        return EnterpriseSubBiz.createList(subBusiness, etc);
    }

    private List<EnterpriseMainBiz> getEnterpriseMainBizs(EnterpriseIntroRequest enterpriseIntroRequest, String etc) {

        List<MainBusiness> mainBusiness = mainBusinessRepository.findMainBusiness(enterpriseIntroRequest.getMainBizCodes());
        return EnterpriseMainBiz.createList(mainBusiness, etc);
    }

}
