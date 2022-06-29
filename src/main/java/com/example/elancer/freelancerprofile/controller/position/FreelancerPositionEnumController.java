package com.example.elancer.freelancerprofile.controller.position;

import com.example.elancer.freelancerprofile.dto.DeveloperSkillsResponse;
import com.example.elancer.freelancerprofile.dto.ProfileEnumResponse;
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

    @GetMapping(FreelancerPositionEnumControllerPath.FREELANCER_PROFILE_ENUMS_FIND)
    public ResponseEntity<ProfileEnumResponse> findProfileEnumNames() {
        ProfileEnumResponse profileEnumNames = freelancerPositionEnumService.findProfileEnumNames();
        return new ResponseEntity<ProfileEnumResponse>(profileEnumNames, HttpStatus.OK);
    }
}
