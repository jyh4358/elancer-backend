package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancerprofile.dto.DeveloperSkillsResponse;
import com.example.elancer.freelancerprofile.service.FreelancerPositionEnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FreelancerPositionEnumController {
    private final FreelancerPositionEnumService freelancerPositionEnumService;

    @GetMapping(FreelancerPositionEnumControllerPath.FREELANCER_POSITION_DEVELOPER_SKILLS_FIND)
    public ResponseEntity<DeveloperSkillsResponse> findDeveloperSkillNames() {
        DeveloperSkillsResponse developerSkillNames = freelancerPositionEnumService.findDeveloperSkillNames();
        return new ResponseEntity<DeveloperSkillsResponse>(developerSkillNames, HttpStatus.OK);
    }




}
