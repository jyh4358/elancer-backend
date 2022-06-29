package com.example.elancer.freelancerprofile.controller.profile;

import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationAndLicenseAndLanguageRequests;
import com.example.elancer.freelancerprofile.dto.request.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.ProjectHistoryCoverRequests;
import com.example.elancer.freelancerprofile.service.FreelancerProfileAlterService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FreelancerProfileAlterController {
    private final FreelancerProfileAlterService freelancerProfileAlterService;

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_INTRO_COVER)
    public ResponseEntity<Void> coverFreelancerIntroduce(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody IntroduceCoverRequest IntroduceCoverRequest
    ) {
        freelancerProfileAlterService.coverFreelancerIntroduce(memberDetails, IntroduceCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_ACADEMIC_COVER)
    public ResponseEntity<Void> coverFreelancerAcademicAbility(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody AcademicAbilityCoverRequests academicAbilityCoverRequests
    ) {
        freelancerProfileAlterService.coverFreelancerAcademicAbility(memberDetails, academicAbilityCoverRequests);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_CAREER_COVER)
    public ResponseEntity<Void> coverFreelancerCareer(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody CareerCoverRequests careerCoverRequests
    ) {
        freelancerProfileAlterService.coverFreelancerCareer(memberDetails, careerCoverRequests);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_PROJECT_HISTORY_COVER)
    public ResponseEntity<Void> coverFreelancerProjectHistory(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody ProjectHistoryCoverRequests projectHistoryCoverRequests
    ) {
        freelancerProfileAlterService.coverFreelancerProjectHistory(memberDetails, projectHistoryCoverRequests);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_EDU_AND_LICENSE_AND_LANG_COVER)
    public ResponseEntity<Void> coverFreelancerEducationAndLicenseAndLanguage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests
    ) {
        freelancerProfileAlterService.coverFreelancerEducationAndLicenseAndLanguage(memberDetails, educationAndLicenseAndLanguageRequests);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
