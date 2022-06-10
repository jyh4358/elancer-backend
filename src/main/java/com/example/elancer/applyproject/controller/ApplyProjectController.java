package com.example.elancer.applyproject.controller;

import com.example.elancer.applyproject.dto.ApplyProjectCreateRequest;
import com.example.elancer.applyproject.service.ApplyProjectService;
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
public class ApplyProjectController {

    private final ApplyProjectService applyProjectService;

    @PostMapping(ApplyProjectControllerPath.CREATE_APPLY_PROJECT)
    public ResponseEntity<Void> createApplyProject(
            @Validated @RequestBody ApplyProjectCreateRequest applyProjectCreateRequest,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        applyProjectService.createApplyProject(applyProjectCreateRequest, memberDetails);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
