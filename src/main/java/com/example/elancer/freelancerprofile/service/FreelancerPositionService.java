package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.checker.RightRequesterChecker;
import com.example.elancer.freelancerprofile.dto.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaSkill;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerPositionService {
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final DeveloperRepository developerRepository;

    @Transactional
    public void coverFreelancerPositionToDeveloper(Long profileNum, MemberDetails memberDetails, DeveloperCoverRequest developerCoverRequest) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Developer.createDeveloper(
                freelancerProfile,
                developerCoverRequest.getFocusSkill(),
                developerCoverRequest.getRole(),
                JavaSkill.createJavaSkill()
                developerCoverRequest.getJavaDetailSkills(),
                developerCoverRequest.getMobileAppDetailSkills(),
                );
    }
}
