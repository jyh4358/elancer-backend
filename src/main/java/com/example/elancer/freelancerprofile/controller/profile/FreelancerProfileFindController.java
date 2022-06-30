package com.example.elancer.freelancerprofile.controller.profile;

import com.example.elancer.freelancerprofile.dto.response.FreelancerDetailResponse;
import com.example.elancer.freelancerprofile.dto.response.FreelancerProfileSimpleResponse;
import com.example.elancer.freelancerprofile.service.profile.FreelancerProfileFindService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FreelancerProfileFindController {
    private final FreelancerProfileFindService freelancerProfileFindService;

    @GetMapping(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_DETAIL)
    public ResponseEntity<FreelancerDetailResponse> findDetailFreelancerProfile(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerDetailResponse detailFreelancerProfile = freelancerProfileFindService.findDetailFreelancerProfile(memberDetails);
        return new ResponseEntity<FreelancerDetailResponse>(detailFreelancerProfile, HttpStatus.OK);
    }

    @GetMapping(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_SIMPLE)
    public ResponseEntity<FreelancerProfileSimpleResponse> findSimpleFreelancerAccount(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerProfileSimpleResponse simpleFreelancerAccount = freelancerProfileFindService.findSimpleFreelancerAccount(memberDetails);
        return new ResponseEntity<FreelancerProfileSimpleResponse>(simpleFreelancerAccount, HttpStatus.OK);
    }
}
