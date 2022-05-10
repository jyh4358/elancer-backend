package com.example.elancer.enterprise.controller;

import com.example.elancer.enterprise.dto.EnterpriseAccountDetailResponse;
import com.example.elancer.enterprise.dto.EnterpriseProfileRequest;
import com.example.elancer.enterprise.dto.EnterpriseProfileResponse;
import com.example.elancer.enterprise.dto.EnterpriseUpdateRequest;
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


    /**
     * 기업 정보 조회
     * @param memberDetails
     * @return
     */
    @GetMapping("/enterprise")
    public ResponseEntity<EnterpriseAccountDetailResponse> findDetailEnterpriseAccount(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        EnterpriseAccountDetailResponse enterpriseAccountInfo = enterpriseService.findDetailEnterpriseAccount(memberDetails.getId());
        return new ResponseEntity<>(enterpriseAccountInfo, HttpStatus.OK);

    }


    /**
     * 기업 정보 수정
     * @param memberDetails
     * @param enterpriseUpdateRequest
     * @return
     */
    @PutMapping("/enterprise")
    public ResponseEntity<Void> coverEnterpriseAccountInfo(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody EnterpriseUpdateRequest enterpriseUpdateRequest
    ) {
        enterpriseService.coverEnterpriseAccountInfo(memberDetails, enterpriseUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    /**
     * 기업 프로필 정보 조회
     * @param memberDetails
     * @return
     */


    @GetMapping("/enterprise/profile")
    public ResponseEntity<EnterpriseProfileResponse> findEnterpriseProfile(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        EnterpriseProfileResponse enterpriseProfileresponse = enterpriseService.findEnterpriseProfile(memberDetails);
        return new ResponseEntity<>(enterpriseProfileresponse, HttpStatus.OK);
    }

    /**
     * 기업 프로필 정보 수정
     * @param memberDetails
     * @param enterpriseProfileRequest
     * @return
     */

    @PutMapping("/enterprise/profile")
    public ResponseEntity<String> coverEnterpriseIntroduce(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody EnterpriseProfileRequest enterpriseProfileRequest) {

        enterpriseService.updateIntro(memberDetails, enterpriseProfileRequest);
        return new ResponseEntity<>("profile ok", HttpStatus.OK);
    }




}
