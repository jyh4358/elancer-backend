package com.example.elancer.waitproject.controller;

import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.waitproject.dto.ExcludeWaitProject;
import com.example.elancer.waitproject.dto.LeaveProjectRequest;
import com.example.elancer.waitproject.dto.WaitProjectRequest;
import com.example.elancer.waitproject.service.WaitProjectService;
import com.example.elancer.waitproject.service.WithdrawFreelancerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WaitProjectController {

    private final WaitProjectService waitProjectService;


    /**
     * 프로젝트에 프리랜서 투입 결정 요청
     * @param memberDetails
     * @param waitProjectRequest
     * @return
     */
    @PostMapping("/wait-project")
    public ResponseEntity<Void> createWaitProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody WaitProjectRequest waitProjectRequest
    ) {
        waitProjectService.createWaitProject(waitProjectRequest, memberDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 프리랜서가 투입 거절
     * @param memberDetails
     * @param leaveProjectRequest
     * @return
     */
    @DeleteMapping("/leave-wait-project")
    public ResponseEntity<Void> leaveWaitProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody LeaveProjectRequest leaveProjectRequest
    ) {
        waitProjectService.leaveProject(leaveProjectRequest, memberDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 기업이 프로젝트에 투입된 프리랜서 투입 철회 요청
     * @param memberDetails
     * @param withdrawFreelancerRequest
     * @return
     */
    @DeleteMapping("/exclude-wait-project")
    public ResponseEntity<Void> excludeWaitProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody WithdrawFreelancerRequest withdrawFreelancerRequest
    ) {
        waitProjectService.excludeProject(withdrawFreelancerRequest, memberDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
