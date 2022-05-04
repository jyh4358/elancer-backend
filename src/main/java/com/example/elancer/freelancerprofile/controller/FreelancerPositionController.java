package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancerprofile.dto.request.position.DesignerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PlannerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PositionEtcCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PublisherCoverRequest;
import com.example.elancer.freelancerprofile.service.FreelancerPositionService;
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

@RestController
@RequiredArgsConstructor
public class FreelancerPositionController {
    private final FreelancerPositionService freelancerPositionService;

    @PutMapping(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_DEVELOPER_COVER)
    public ResponseEntity<Void> coverFreelancerPositionToDeveloper(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody DeveloperCoverRequest developerCoverRequest
    ) {
        freelancerPositionService.coverFreelancerPositionToDeveloper(profileNum, memberDetails, developerCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_PUBLISHER_COVER)
    public ResponseEntity<Void> coverFreelancerPositionToPublisher(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody PublisherCoverRequest publisherCoverRequest
    ) {
        freelancerPositionService.coverFreelancerPositionToPublisher(profileNum, memberDetails, publisherCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_DESIGNER_COVER)
    public ResponseEntity<Void> coverFreelancerPositionToDesigner(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody DesignerCoverRequest designerCoverRequest
    ) {
        freelancerPositionService.coverFreelancerPositionToDesigner(profileNum, memberDetails, designerCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_PLANNER_COVER)
    public ResponseEntity<Void> coverFreelancerPositionToPlanner(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody PlannerCoverRequest plannerCoverRequest
    ) {
        freelancerPositionService.coverFreelancerPositionToPlanner(profileNum, memberDetails, plannerCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_CROWD_WORKER_COVER)
    public ResponseEntity<Void> coverFreelancerPositionToCrowdWorker(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        freelancerPositionService.coverFreelancerPositionToCrowdWorker(profileNum, memberDetails);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping(FreelancerPositionControllerPath.FREELANCER_PROFILE_POSITION_ETC_COVER)
    public ResponseEntity<Void> coverFreelancerPositionToEtc(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody PositionEtcCoverRequest positionEtcCoverRequest
    ) {
        freelancerPositionService.coverFreelancerPositionToEtc(profileNum, memberDetails, positionEtcCoverRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
