package com.example.elancer.freelancerprofile.controller.position;

import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponses;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.service.position.FreelancerPositionSearchService;
import com.example.elancer.freelancerprofile.model.WorkArea;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FreelancerPositionSearchController {
    private final FreelancerPositionSearchService freelancerPositionSearchService;

    @GetMapping(FreelancerPositionSearchControllerPath.FREELANCER_DEVELOPER_SEARCH)
    public ResponseEntity<FreelancerSimpleResponses> searchDevelopers(
            @RequestParam(required = false) PositionType positionType,
            @RequestParam(required = false) String majorSkillKeywords,
            @RequestParam(required = false) HopeWorkState hopeWorkState,
            @RequestParam(required = false) PositionWorkManShip positionWorkManShip,
            @RequestParam(required = false) WorkArea workArea,
            @PageableDefault(sort = "freelancerNum", direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDevelopers(positionType, majorSkillKeywords, hopeWorkState, positionWorkManShip, workArea, pageable, memberDetails);
        return new ResponseEntity<FreelancerSimpleResponses>(freelancerSimpleResponses, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionSearchControllerPath.FREELANCER_PUBLISHER_SEARCH)
    public ResponseEntity<FreelancerSimpleResponses> searchPublishers(
            @RequestParam(required = false) PositionType positionType,
            @RequestParam(required = false) String majorSkillKeywords,
            @RequestParam(required = false) HopeWorkState hopeWorkState,
            @RequestParam(required = false) PositionWorkManShip positionWorkManShip,
            @RequestParam(required = false) WorkArea workArea,
            @PageableDefault(sort = "num", direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchPublishers(positionType, majorSkillKeywords, hopeWorkState, positionWorkManShip, workArea, pageable, memberDetails);
        return new ResponseEntity<FreelancerSimpleResponses>(freelancerSimpleResponses, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionSearchControllerPath.FREELANCER_DESIGNER_SEARCH)
    public ResponseEntity<FreelancerSimpleResponses> searchDesigners(
            @RequestParam(required = false) PositionType positionType,
            @RequestParam(required = false) String majorSkillKeywords,
            @RequestParam(required = false) HopeWorkState hopeWorkState,
            @RequestParam(required = false) PositionWorkManShip positionWorkManShip,
            @RequestParam(required = false) WorkArea workArea,
            @PageableDefault(sort = "num", direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchDesigners(positionType, majorSkillKeywords, hopeWorkState, positionWorkManShip, workArea, pageable, memberDetails);
        return new ResponseEntity<FreelancerSimpleResponses>(freelancerSimpleResponses, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionSearchControllerPath.FREELANCER_PLANNER_SEARCH)
    public ResponseEntity<FreelancerSimpleResponses> searchPlanners(
            @RequestParam(required = false) PositionType positionType,
            @RequestParam(required = false) String majorSkillKeywords,
            @RequestParam(required = false) HopeWorkState hopeWorkState,
            @RequestParam(required = false) PositionWorkManShip positionWorkManShip,
            @RequestParam(required = false) WorkArea workArea,
            @PageableDefault(sort = "num", direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchPlanners(positionType, majorSkillKeywords, hopeWorkState, positionWorkManShip, workArea, pageable, memberDetails);
        return new ResponseEntity<FreelancerSimpleResponses>(freelancerSimpleResponses, HttpStatus.OK);
    }

    @GetMapping(FreelancerPositionSearchControllerPath.FREELANCER_POSITION_ETC_SEARCH)
    public ResponseEntity<FreelancerSimpleResponses> searchPositionEtc(
            @RequestParam(required = false) PositionType positionType,
            @RequestParam(required = false) String majorSkillKeywords,
            @RequestParam(required = false) HopeWorkState hopeWorkState,
            @RequestParam(required = false) PositionWorkManShip positionWorkManShip,
            @RequestParam(required = false) WorkArea workArea,
            @PageableDefault(sort = "num", direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        FreelancerSimpleResponses freelancerSimpleResponses = freelancerPositionSearchService.searchPositionEtc(positionType, majorSkillKeywords, hopeWorkState, positionWorkManShip, workArea, pageable, memberDetails);
        return new ResponseEntity<FreelancerSimpleResponses>(freelancerSimpleResponses, HttpStatus.OK);
    }
}
