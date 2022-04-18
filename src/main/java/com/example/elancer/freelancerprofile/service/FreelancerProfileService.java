package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.checker.RightRequesterChecker;
import com.example.elancer.freelancer.dto.FreelancerProfileIntroSaveOrUpdateRequest;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerProfileService {
    private final FreelancerProfileRepository freelancerProfileRepository;

    @Transactional
    public void saveOrUpdateFreelancerProfileIntro(MemberDetails memberDetails, Long profileNum, FreelancerProfileIntroSaveOrUpdateRequest freelancerProfileIntroSaveOrUpdateRequest) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        freelancerProfile.coverIntroduceInFreelancer(
                freelancerProfileIntroSaveOrUpdateRequest.getIntroName(),
                freelancerProfileIntroSaveOrUpdateRequest.getIntroBackGround(),
                freelancerProfileIntroSaveOrUpdateRequest.getIntroVideoUrl(),
                freelancerProfileIntroSaveOrUpdateRequest.getIntroContent()
        );
    }
}
