package com.example.elancer.enterprise.controller;

import com.example.elancer.enterprise.dto.EnterpriseIntroRequest;
import com.example.elancer.enterprise.dto.EnterpriseJoinAndUpdateRequest;
import com.example.elancer.enterprise.service.EnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @PostMapping("/enterprise")
    public ResponseEntity<String> joinEnterprise(
            @Validated @RequestBody EnterpriseJoinAndUpdateRequest enterpriseJoinAndUpdateRequest) {

        enterpriseService.join(enterpriseJoinAndUpdateRequest);
        return new ResponseEntity("join", HttpStatus.CREATED);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity enterpriseIntro(@PathVariable String id) {
//        enterpriseService.(id);
        return null;
    }

    @PutMapping("/enterprise/{id}")
    public ResponseEntity<String> updateEnterpriseIntro(
            @PathVariable Long id,
            @Validated @RequestBody EnterpriseIntroRequest enterpriseIntroRequest) {
        enterpriseService.updateIntro(id, enterpriseIntroRequest, null);


        return new ResponseEntity("update", HttpStatus.OK);
    }
}
