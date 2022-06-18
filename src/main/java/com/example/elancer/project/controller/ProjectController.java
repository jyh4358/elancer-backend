package com.example.elancer.project.controller;

import com.example.elancer.enterprise.dto.EnterpriseSimpleDetailResponse;
import com.example.elancer.enterprise.service.EnterpriseService;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.dto.ProjectDeleteRequest;
import com.example.elancer.project.dto.ProjectProcessingRequest;
import com.example.elancer.project.dto.ProjectSaveRequest;
import com.example.elancer.project.dto.RecommendProjectResponse;
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
    ) {
        projectService.deleteProject(memberDetails, projectDeleteRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 프로젝트 진행 요청
     *
     * @param memberDetails
     * @param projectProcessingRequest
     */
    @PostMapping("/start-project")
    public ResponseEntity<Void> startProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody ProjectProcessingRequest projectProcessingRequest
    ) {
        projectService.startProject(memberDetails, projectProcessingRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/finish-project")
    public ResponseEntity<Void> finishProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody ProjectProcessingRequest projectProcessingRequest
    ) {
        projectService.finishProject(memberDetails, projectProcessingRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/recommend-project")
//    public ResponseEntity<RecommendProjectResponse> RecommendProject() {
//
//
//    }
}
