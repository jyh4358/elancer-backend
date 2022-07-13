package com.example.elancer.project.controller;

import com.example.elancer.enterprise.dto.EnterpriseSimpleDetailResponse;
import com.example.elancer.enterprise.service.EnterpriseService;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.dto.*;
import com.example.elancer.project.model.PositionKind;
import com.example.elancer.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final EnterpriseService enterpriseService;

    @GetMapping("/project/{projectNum}")
    public ResponseEntity<ProjectDetailResponse> detailProject(
            @PathVariable Long projectNum
    ) {
        ProjectDetailResponse projectDetailResponse = projectService.findDetailProject(projectNum);

        return new ResponseEntity<>(projectDetailResponse, HttpStatus.OK);
    }

    @GetMapping("/project-index-list")
    public ResponseEntity<IndexProjectResponse> findIndexProjectList() {

        IndexProjectResponse indexProjectList = projectService.findIndexProjectList();

        return new ResponseEntity<>(indexProjectList, HttpStatus.OK);
    }

    @GetMapping("/project-list")
    public ResponseEntity<InfinityListResponse> findProjectList(
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String skill,
            @ModelAttribute ProjectSearchCondition projectSearchCondition,
            @PageableDefault(size = 10, sort = "num",direction = Sort.Direction.DESC) Pageable pageable

    ) {
        Slice<ProjectBoxResponse> projectBoxResponses = projectService.searchProjectList(position, skill, projectSearchCondition, pageable);
        InfinityListResponse infinityListResponse = InfinityListResponse.of(projectBoxResponses.getContent(), !projectBoxResponses.isLast());
        return new ResponseEntity<>(infinityListResponse, HttpStatus.OK);
    }


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

    @GetMapping("/recommend-project")
    public ResponseEntity<List<ProjectBoxResponse>> RecommendProject() {
        List<ProjectBoxResponse> recommendProject = projectService.findRecommendProject();

        return new ResponseEntity<>(recommendProject, HttpStatus.OK);
    }

    @GetMapping("/project-list-count")
    public ResponseEntity<ProjectListCount> projectListCount(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return new ResponseEntity<>(projectService.projectCount(memberDetails), HttpStatus.OK);
    }

    @GetMapping("/enterprise-project")
    public ResponseEntity<List<DashboardProjectResponse>> dashboardProjectList(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {

        List<DashboardProjectResponse> dashboardProject = projectService.findDashboardProject(memberDetails);

        return new ResponseEntity<>(dashboardProject, HttpStatus.OK);
    }

    @GetMapping("/apply-project")
    public ResponseEntity<List<ApplyProjectResponse>> applyProjectList(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        List<ApplyProjectResponse> applyProject = projectService.findApplyProject(memberDetails);
        return new ResponseEntity<>(applyProject, HttpStatus.OK);
    }

    @GetMapping("/interview-project")
    public ResponseEntity<List<InterviewProjectResponse>> interviewProjectList(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        List<InterviewProjectResponse> interviewProject = projectService.findInterviewProject(memberDetails);
        return new ResponseEntity<>(interviewProject, HttpStatus.OK);
    }

    @GetMapping("/wait-project")
    public ResponseEntity<List<WaitProjectResponse>> waitProjectList(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        List<WaitProjectResponse> waitProject = projectService.findWaitProject(memberDetails);

        return new ResponseEntity<>(waitProject, HttpStatus.OK);
    }

    @GetMapping("/processing-project")
    public ResponseEntity<List<ProcessingProjectResponse>> processingProjectList(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        List<ProcessingProjectResponse> processingProjectResponses = projectService.findProcessingProject(memberDetails);
        return new ResponseEntity<>(processingProjectResponses, HttpStatus.OK);
    }

    @GetMapping("/finish-project")
    public ResponseEntity<List<ProcessingProjectResponse>> finishProjectList(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        List<ProcessingProjectResponse> finishProject = projectService.findFinishProject(memberDetails);
        return new ResponseEntity<>(finishProject, HttpStatus.OK);
    }


}
