package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancer.model.HopeWorkState;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.PositionWorkManShip;
import com.example.elancer.freelancerprofile.service.FreelancerPositionSearchService;
import com.example.elancer.freelancerprofile.model.WorkArea;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FreelancerPositionSearchController {
    private final FreelancerPositionSearchService freelancerPositionSearchService;

    @GetMapping(FreelancerPositionSearchControllerPath.FREELANCER_DEVELOPER_SEARCH)
    public ResponseEntity<FreelancerSimpleResponse> searchDevelopers(
            @RequestParam(required = false) PositionType positionType,
            @RequestParam(required = false) List<String> majorSkillKeywords,
            @RequestParam(required = false) String minorSkill,
            @RequestParam(required = false) List<HopeWorkState> hopeWorkStates,
            @RequestParam(required = false) List<PositionWorkManShip> positionWorkManShips,
            @RequestParam(required = false) WorkArea workArea
    ) {
        freelancerPositionSearchService.searchDevelopers(positionType, majorSkillKeywords, minorSkill, hopeWorkStates, positionWorkManShips, workArea);
        return new ResponseEntity<FreelancerSimpleResponse>(HttpStatus.OK);
    }
}
