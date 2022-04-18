package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancer.controller.FreelancerControllerPath;
import com.example.elancer.freelancer.dto.FreelancerIntroSaveOrUpdateRequest;
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

//    @PostMapping(FreelancerControllerPath.FREELANCER_INTRO_SAVE)
//    public ResponseEntity<Void> saveOrUpdateFreelancerIntro(
//            @PathVariable Long freelancerNum,
//            @AuthenticationPrincipal MemberDetails memberDetails,
//            @Validated @RequestBody FreelancerIntroSaveOrUpdateRequest freelancerIntroSaveOrUpdateRequest
//    ) {
//        freelancerService.saveOrUpdateFreelancerIntro(memberDetails, freelancerNum, freelancerIntroSaveOrUpdateRequest);
//        return null;
//    }
}
