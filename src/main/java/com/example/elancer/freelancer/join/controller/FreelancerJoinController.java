package com.example.elancer.freelancer.join.controller;

import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.join.service.FreelancerJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FreelancerJoinController {
    private final FreelancerJoinService freelancerJoinService;

    @PostMapping(FreelancerJoinControllerPath.FREELANCER_JOIN)
    public ResponseEntity<Void> joinFreelancer(
            @Validated @RequestBody FreelancerJoinRequest freelancerJoinRequest
    ) {
        freelancerJoinService.joinFreelancer(freelancerJoinRequest);
        return ResponseEntity.ok().body(null);
    }
}
