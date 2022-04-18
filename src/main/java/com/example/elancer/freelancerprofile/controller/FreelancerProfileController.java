package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancer.controller.FreelancerControllerPath;
import com.example.elancer.freelancer.dto.FreelancerProfileIntroSaveOrUpdateRequest;
import com.example.elancer.freelancerprofile.service.FreelancerProfileService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FreelancerProfileController {
    private final FreelancerProfileService freelancerProfileService;

    @PostMapping(FreelancerProfileControllerPath.FREELANCER_PROFILE_INTRO_SAVE)
    public ResponseEntity<Void> saveOrUpdateFreelancerIntro(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody FreelancerProfileIntroSaveOrUpdateRequest freelancerIntroSaveOrUpdateRequest
    ) {
        freelancerProfileService.saveOrUpdateFreelancerProfileIntro(memberDetails, profileNum, freelancerIntroSaveOrUpdateRequest);
        return null;
    }
}
