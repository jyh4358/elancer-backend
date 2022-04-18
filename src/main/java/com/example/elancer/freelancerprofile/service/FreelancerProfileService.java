package com.example.elancer.freelancerprofile.service;

import com.example.elancer.common.checker.RightRequesterChecker;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.academic.AcademicRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreelancerProfileService {
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final AcademicRepository academicRepository;

    @Transactional
    public void coverFreelancerProfileIntro(MemberDetails memberDetails, Long profileNum, IntroduceCoverRequest introduceCoverRequest) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        freelancerProfile.coverIntroduceInFreelancer(
                introduceCoverRequest.getIntroName(),
                introduceCoverRequest.getIntroBackGround(),
                introduceCoverRequest.getIntroVideoUrl(),
                introduceCoverRequest.getIntroContent()
        );
    }

    @Transactional
    public void coverFreelancerAcademicAbility(MemberDetails memberDetails, Long profileNum, AcademicAbilityCoverRequests academicAbilityCoverRequests) {
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findById(profileNum).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequesterChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        List<AcademicAbility> academicAbilities = academicAbilityCoverRequests.getAcademicAbilityCoverRequests().stream()
                .map(AcademicAbilityCoverRequest::ofToAcademicAbility)
                .collect(Collectors.toList());

        freelancerProfile.coverAcademicAbilities(academicAbilities);
    }
}
