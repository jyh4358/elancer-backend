package com.example.elancer.enterprise.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import com.example.elancer.enterprise.domain.enterpriseintro.*;
import com.example.elancer.enterprise.dto.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnterpriseService {

    private final PasswordEncoder passwordEncoder;
    private final EnterpriseRepository enterpriseRepository;
    private final MainBusinessRepository mainBusinessRepository;
    private final SubBusinessRepository subBusinessRepository;


    /**
     * 기업 정보 조회
     * @param num
     * @return
     */
    public EnterpriseAccountDetailResponse findDetailEnterpriseAccount(Long num) {
        Enterprise enterprise = enterpriseRepository.findById(num).orElseThrow(NotExistEnterpriseException::new);
        return EnterpriseAccountDetailResponse.of(enterprise);
    }


    /**
     * 기업 정보 수정
     * @param memberDetails
     * @param enterpriseUpdateRequest
     */
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


    /**
     * 기업 프로필 정보 조회
     * @param memberDetails
     * @return
     */
    public EnterpriseProfileResponse findEnterpriseProfile(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);
        return EnterpriseProfileResponse.of(enterprise);

    }


    /**
     * 기업 프로필 업데이트
     * @param num
     * @param enterpriseProfileRequest
     */
    @Transactional
    public void updateIntro(
            Long num, EnterpriseProfileRequest enterpriseProfileRequest) {

        Enterprise enterprise = enterpriseRepository.findById(num).orElseThrow(NotExistEnterpriseException::new);

        List<EnterpriseMainBiz> enterpriseMainBizs = getEnterpriseMainBizs(enterpriseProfileRequest);
        List<EnterpriseSubBiz> enterpriseSubBizs = getEnterpriseSubBizs(enterpriseProfileRequest);


        EnterpriseIntro enterpriseIntro = EnterpriseIntro.of(enterpriseProfileRequest.getIntroTitle(), enterpriseMainBizs, enterpriseSubBizs, enterprise);


        enterprise.updateIntro(enterpriseIntro,
                enterpriseProfileRequest.getBizContents(),
                enterpriseProfileRequest.getSales(),
                enterpriseProfileRequest.getIdNumber());

    }

    /**
     * 인재 스크랩 조회
     * @param id
     */
    public void findScrap(Long id) {
        Enterprise enterprise = enterpriseRepository.findScrapAndFreelancerFetchJoin(id).orElseThrow(NotExistEnterpriseException::new);
        List<Freelancer> collect = enterprise.getHeartScraps().stream().map(scrap -> scrap.getFreelancer()).collect(Collectors.toList());
        // todo - entityy -> dto 변환 로직 후 반환

    }


    /**
     * 서비스 로직
     */

    private void checkDuplicate(String userId) {
        if (enterpriseRepository.existsByUserId(userId)) {
            throw new EnterpriseCheckUserIdException();
        }
    }
    private List<EnterpriseSubBiz> getEnterpriseSubBizs(EnterpriseProfileRequest enterpriseProfileRequest) {
        List<SubBusiness> subBusiness = subBusinessRepository.findSubBusiness(enterpriseProfileRequest.getSubBizCodes());
        return EnterpriseSubBiz.createList(subBusiness);
    }

    private List<EnterpriseMainBiz> getEnterpriseMainBizs(EnterpriseProfileRequest enterpriseProfileRequest) {

        List<MainBusiness> mainBusiness = mainBusinessRepository.findMainBusiness(enterpriseProfileRequest.getMainBizCodes());
        return EnterpriseMainBiz.createList(mainBusiness);
    }

}
