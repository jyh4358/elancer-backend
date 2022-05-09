package com.example.elancer.enterprise.controller;

import com.example.elancer.enterprise.dto.EnterpriseIntroRequest;
import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
import com.example.elancer.enterprise.service.EnterpriseService;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

//    @PostMapping("/enterprise")
    public ResponseEntity<String> joinEnterprise(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody EnterpriseJoinRequest enterpriseJoinRequest) {

        enterpriseService.join(enterpriseJoinRequest);
        return new ResponseEntity("join", HttpStatus.CREATED);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity enterpriseIntro(@PathVariable String id) {
//        enterpriseService.(id);
        return null;
    }

//    @PostMapping("/enterprise/{num}/profile")
//    public ResponseEntity<String> enterpriseProfile(
//            @PathVariable Long num,
//            @AuthenticationPrincipal MemberDetails memberDetails,
//            @Validated @RequestBody EnterpriseIntroRequest enterpriseIntroRequest) {
//
//        enterpriseService.updateIntro(num, enterpriseIntroReques;
//    }

    @PutMapping("/enterprise/{id}")
    public ResponseEntity<String> updateEnterpriseIntro(
            @PathVariable Long id,
            @Validated @RequestBody EnterpriseIntroRequest enterpriseIntroRequest) {
        enterpriseService.updateIntro(id, enterpriseIntroRequest, null);


        return new ResponseEntity("update", HttpStatus.OK);
    }



}
