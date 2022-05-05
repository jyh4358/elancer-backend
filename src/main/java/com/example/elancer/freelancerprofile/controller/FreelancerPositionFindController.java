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
    public ResponseEntity<DeveloperResponse> coverFreelancerPositionToDeveloper(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        DeveloperResponse developerResponse = freelancerPositionFindService.coverFreelancerPositionToDeveloper(profileNum, memberDetails);
        return new ResponseEntity<DeveloperResponse>(developerResponse, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PUBLISHER_FIND)
    public ResponseEntity<PublisherResponse> coverFreelancerPositionToPublisher(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        PublisherResponse publisherResponse = freelancerPositionFindService.coverFreelancerPositionToPublisher(profileNum, memberDetails);
        return new ResponseEntity<PublisherResponse>(publisherResponse, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DESIGNER_FIND)
    public ResponseEntity<DesignerResponse> coverFreelancerPositionToDesigner(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        DesignerResponse designerResponse = freelancerPositionFindService.coverFreelancerPositionToDesigner(profileNum, memberDetails);
        return new ResponseEntity<DesignerResponse>(designerResponse, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PLANNER_FIND)
    public ResponseEntity<PlannerResponse> coverFreelancerPositionToPlanner(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        PlannerResponse plannerResponse = freelancerPositionFindService.coverFreelancerPositionToPlanner(profileNum, memberDetails);
        return new ResponseEntity<PlannerResponse>(plannerResponse, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_ETC_FIND)
    public ResponseEntity<PositionEtcResponse> coverFreelancerPositionToEtc(
            @PathVariable Long profileNum,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        PositionEtcResponse positionEtcResponse = freelancerPositionFindService.coverFreelancerPositionToEtc(profileNum, memberDetails);
        return new ResponseEntity<PositionEtcResponse>(positionEtcResponse, HttpStatus.OK);
    }
}
