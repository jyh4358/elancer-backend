package com.example.elancer.interviewproject.controller;

import com.example.elancer.interviewproject.dto.InterviewProjectRequest;
import com.example.elancer.interviewproject.service.InterviewProjectService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterviewProjectController {

    private final InterviewProjectService interviewProjectService;

    @PostMapping("/interview-project")
    public ResponseEntity<Void> createInterviewProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody InterviewProjectRequest interviewProjectRequest
    ) {
        interviewProjectService.createInterviewProject(interviewProjectRequest, memberDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
