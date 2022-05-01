package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancerprofile.dto.response.FreelancerDetailResponse;
import com.example.elancer.freelancerprofile.dto.response.FreelancerProfileSimpleResponse;
import com.example.elancer.freelancerprofile.service.FreelancerProfileFindService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class FreelancerProfileFindController {
    private final FreelancerProfileFindService freelancerProfileFindService;

    @GetMapping(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_DETAIL)
    public ResponseEntity<FreelancerDetailResponse> findDetailFreelancerProfile(
            @NotNull @PathVariable Long freelancerNum,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerDetailResponse detailFreelancerProfile = freelancerProfileFindService.findDetailFreelancerProfile(freelancerNum, memberDetails);
        return new ResponseEntity<FreelancerDetailResponse>(detailFreelancerProfile, HttpStatus.OK);
    }

    @GetMapping(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_SIMPLE)
    public ResponseEntity<FreelancerProfileSimpleResponse> findSimpleFreelancerAccount(
            @NotNull @PathVariable Long freelancerNum,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerProfileSimpleResponse simpleFreelancerAccount = freelancerProfileFindService.findSimpleFreelancerAccount(freelancerNum, memberDetails);
        return new ResponseEntity<FreelancerProfileSimpleResponse>(simpleFreelancerAccount, HttpStatus.OK);
    }
}
