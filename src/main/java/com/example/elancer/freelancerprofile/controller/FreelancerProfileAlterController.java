package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancerprofile.dto.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.EducationAndLicenseAndLanguageRequests;
import com.example.elancer.freelancerprofile.dto.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.dto.ProjectHistoryCoverRequest;
import com.example.elancer.freelancerprofile.service.FreelancerProfileAlterService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FreelancerProfileAlterController {
    private final FreelancerProfileAlterService freelancerProfileAlterService;

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_INTRO_COVER)
    public ResponseEntity<Void> coverFreelancerIntroduce(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody IntroduceCoverRequest IntroduceCoverRequest
    ) {
        freelancerProfileAlterService.coverFreelancerIntroduce(memberDetails, profileNum, IntroduceCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_ACADEMIC_COVER)
    public ResponseEntity<Void> coverFreelancerAcademicAbility(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody AcademicAbilityCoverRequests academicAbilityCoverRequests
    ) {
        freelancerProfileAlterService.coverFreelancerAcademicAbility(memberDetails, profileNum, academicAbilityCoverRequests);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_CAREER_COVER)
    public ResponseEntity<Void> coverFreelancerCareer(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody CareerCoverRequests careerCoverRequests
    ) {
        freelancerProfileAlterService.coverFreelancerCareer(memberDetails, profileNum, careerCoverRequests);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_PROJECT_HISTORY_COVER)
    public ResponseEntity<Void> coverFreelancerProjectHistory(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody ProjectHistoryCoverRequest projectHistoryCoverRequest
    ) {
        freelancerProfileAlterService.coverFreelancerProjectHistory(memberDetails, profileNum, projectHistoryCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_EDU_AND_LICENSE_AND_LANG_COVER)
    public ResponseEntity<Void> coverFreelancerEducationAndLicenseAndLanguage(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests
    ) {
        freelancerProfileAlterService.coverFreelancerEducationAndLicenseAndLanguage(memberDetails, profileNum, educationAndLicenseAndLanguageRequests);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
