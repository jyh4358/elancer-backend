package com.example.elancer.freelancer.controller;

import com.example.elancer.freelancer.dto.FreelancerAccountCoverRequest;
import com.example.elancer.freelancer.dto.FreelancerAccountDetailResponse;
import com.example.elancer.freelancer.service.FreelancerService;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class FreelancerController {
    private final FreelancerService freelancerService;

    @PutMapping(FreelancerControllerPath.FREELANCER_ACCOUNT_INFO_UPDATE)
    public ResponseEntity<Void> coverFreelancerAccountInfo(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody FreelancerAccountCoverRequest freelancerAccountCoverRequest
    ) {
        freelancerService.coverFreelancerAccountInfo(memberDetails, freelancerAccountCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(FreelancerControllerPath.FREELANCER_ACCOUNT_INFO_FIND)
    public ResponseEntity<FreelancerAccountDetailResponse> findDetailFreelancerAccount(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerAccountDetailResponse freelancerAccountInfo = freelancerService.findDetailFreelancerAccount(memberDetails);
        return new ResponseEntity<FreelancerAccountDetailResponse>(freelancerAccountInfo, HttpStatus.OK);
    }
    //Todo min 예외 핸들러 필요 - 서비스 로직 참고해서 추가할것.
}
