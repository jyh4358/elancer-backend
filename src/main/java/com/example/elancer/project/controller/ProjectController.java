package com.example.elancer.project.controller;

import com.example.elancer.enterprise.dto.EnterpriseSimpleDetailResponse;
import com.example.elancer.enterprise.service.EnterpriseService;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.dto.ProjectDeleteRequest;
import com.example.elancer.project.dto.ProjectSaveRequest;
import com.example.elancer.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final EnterpriseService enterpriseService;


    @GetMapping("/project-save")
    public ResponseEntity<EnterpriseSimpleDetailResponse> saveProject(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        EnterpriseSimpleDetailResponse simpleEnterpriseInfo = enterpriseService.findSimpleEnterpriseInfo(memberDetails);

        return new ResponseEntity<>(simpleEnterpriseInfo, HttpStatus.OK);
    }

    @PostMapping("/project-save")
    public ResponseEntity<Void> saveProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody ProjectSaveRequest projectSaveRequest
    ) {
        projectService.saveProject(memberDetails, projectSaveRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/project-delete")
    public ResponseEntity<Void> deleteProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody ProjectDeleteRequest projectDeleteRequest
    ){
        projectService.deleteProject(memberDetails, projectDeleteRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
