package com.example.elancer.enterprise.join.controller;

import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
import com.example.elancer.enterprise.join.service.EnterpriseJoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EnterpriseJoinController {

    private final EnterpriseJoinService enterpriseJoinService;

    @PostMapping("/enterprise")
    public ResponseEntity<Void> joinEnterprise(
            @Validated @RequestBody EnterpriseJoinRequest enterpriseJoinRequest
    ) {
        enterpriseJoinService.joinEnterprise(enterpriseJoinRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
