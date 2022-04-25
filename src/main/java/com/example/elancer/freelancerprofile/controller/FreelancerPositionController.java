package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancerprofile.dto.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.dto.PublisherCoverRequest;
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
}
