package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancerprofile.dto.PublisherResponse;
import com.example.elancer.freelancerprofile.dto.DesignerResponse;
import com.example.elancer.freelancerprofile.dto.DeveloperResponse;
import com.example.elancer.freelancerprofile.dto.PlannerResponse;
import com.example.elancer.freelancerprofile.dto.PositionEtcResponse;
import com.example.elancer.freelancerprofile.service.FreelancerPositionFindService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FreelancerPositionFindController {
    private final FreelancerPositionFindService freelancerPositionFindService;

    @GetMapping(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DEVELOPER_FIND)
    public ResponseEntity<DeveloperResponse> findFreelancerPositionToDeveloper(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        DeveloperResponse developerResponse = freelancerPositionFindService.findFreelancerPositionToDeveloper(memberDetails);
        return new ResponseEntity<DeveloperResponse>(developerResponse, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PUBLISHER_FIND)
    public ResponseEntity<PublisherResponse> findFreelancerPositionToPublisher(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        PublisherResponse publisherResponse = freelancerPositionFindService.findFreelancerPositionToPublisher(memberDetails);
        return new ResponseEntity<PublisherResponse>(publisherResponse, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DESIGNER_FIND)
    public ResponseEntity<DesignerResponse> findFreelancerPositionToDesigner(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        DesignerResponse designerResponse = freelancerPositionFindService.findFreelancerPositionToDesigner(memberDetails);
        return new ResponseEntity<DesignerResponse>(designerResponse, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PLANNER_FIND)
    public ResponseEntity<PlannerResponse> findFreelancerPositionToPlanner(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        PlannerResponse plannerResponse = freelancerPositionFindService.findFreelancerPositionToPlanner(memberDetails);
        return new ResponseEntity<PlannerResponse>(plannerResponse, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_ETC_FIND)
    public ResponseEntity<PositionEtcResponse> findFreelancerPositionToEtc(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        PositionEtcResponse positionEtcResponse = freelancerPositionFindService.findFreelancerPositionToEtc(memberDetails);
        return new ResponseEntity<PositionEtcResponse>(positionEtcResponse, HttpStatus.OK);
    }

    //TODO 예외핸들러 추가해야한다.

}
