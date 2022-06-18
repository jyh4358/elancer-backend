package com.example.elancer.interviewproject.controller;

import com.example.elancer.interviewproject.dto.*;
import com.example.elancer.interviewproject.service.InterviewProjectService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InterviewProjectController {

    private final InterviewProjectService interviewProjectService;

    /**
     * 프로젝트 인터뷰 리스트 요청
     * @param memberDetails
     * @param interviewProjectRequest
     * @return
     */
    @GetMapping("/interview-project")
    public ResponseEntity<List<InterviewProjectResponse>> interviewProjectList(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody InterviewProjectRequest interviewProjectRequest
    ) {
        List<InterviewProjectResponse> interviewProjectResponses = interviewProjectService.interviewProjectList(interviewProjectRequest, memberDetails);
        return new ResponseEntity<>(interviewProjectResponses, HttpStatus.OK);
    }

    /**
     * 기업이 프리랜서에게 인터뷰 요청
     * @param memberDetails
     * @param createInterviewProjectRequest
     * @return
     */
    @PostMapping("/interview-project")
    public ResponseEntity<Void> createInterviewProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody CreateInterviewProjectRequest createInterviewProjectRequest
    ) {
        interviewProjectService.createInterviewProject(createInterviewProjectRequest, memberDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 프리랜서가 인터뷰 수락
     * @param memberDetails
     * @param acceptInterviewRequest
     * @return
     */

    @PostMapping("/accept-interview-project")
    public ResponseEntity<Void> acceptInterview(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody AcceptInterviewRequest acceptInterviewRequest
    ) {
        interviewProjectService.acceptInterview(memberDetails, acceptInterviewRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 기업이 인터뷰 철회
     * @param memberDetails
     * @param rejectInterviewRequest
     * @return
     */
    @PostMapping("/reject-interview-project")
    public ResponseEntity<Void> rejectInterview(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody RejectInterviewRequest rejectInterviewRequest
    ) {
        interviewProjectService.rejectInterview(memberDetails, rejectInterviewRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
