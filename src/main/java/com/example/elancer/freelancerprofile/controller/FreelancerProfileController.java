package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.service.FreelancerProfileService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FreelancerProfileController {
    private final FreelancerProfileService freelancerProfileService;

    @PutMapping(FreelancerProfileControllerPath.FREELANCER_PROFILE_INTRO_COVER)
    public ResponseEntity<Void> coverFreelancerIntro(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody IntroduceCoverRequest IntroduceCoverRequest
    ) {
        freelancerProfileService.coverFreelancerProfileIntro(memberDetails, profileNum, IntroduceCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(FreelancerProfileControllerPath.FREELANCER_PROFILE_ACADEMIC_COVER)
    public ResponseEntity<Void> coverFreelancerAcademicAbility(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody AcademicAbilityCoverRequests academicAbilityCoverRequests
    ) {
        freelancerProfileService.coverFreelancerAcademicAbility(memberDetails, profileNum, academicAbilityCoverRequests);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
