package com.example.elancer.freelancer.controller;

import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.service.FreelancerService;
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

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class FreelancerController {
    private final FreelancerService freelancerService;

    @PutMapping(FreelancerControllerPath.FREELANCER_ACCOUNT_INFO_UPDATE)
    public ResponseEntity<Void> coverFreelancerAccountInfo(
            @NotNull @PathVariable Long freelancerNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody FreelancerAccountCoverRequest freelancerAccountCoverRequest
    ) {
        freelancerService.coverFreelancerAccountInfo(freelancerNum, memberDetails, freelancerAccountCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
