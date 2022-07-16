package com.example.elancer.enterprise.controller;

import com.example.elancer.enterprise.dto.*;
import com.example.elancer.enterprise.service.EnterpriseService;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        EnterpriseAccountDetailResponse enterpriseAccountInfo = enterpriseService.findDetailEnterpriseAccount(memberDetails);
        return new ResponseEntity<>(enterpriseAccountInfo, HttpStatus.OK);

    }


    /**
     * 기업 정보 수정
     * @param memberDetails
     * @param enterpriseUpdateRequest
     * @return
     */
    @PutMapping("/enterprise")
    public ResponseEntity<EnterpriseAccountDetailResponse> coverEnterpriseAccountInfo(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody EnterpriseUpdateRequest enterpriseUpdateRequest
    ) {
        EnterpriseAccountDetailResponse enterpriseAccountInfo = enterpriseService.coverEnterpriseAccountInfo(memberDetails, enterpriseUpdateRequest);
        return new ResponseEntity<>(enterpriseAccountInfo, HttpStatus.OK);
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
        EnterpriseProfileResponse enterpriseProfileResponse = enterpriseService.findEnterpriseProfile(memberDetails);
        return new ResponseEntity<>(enterpriseProfileResponse, HttpStatus.OK);
    }

    /**
     * 기업 프로필 정보 수정
     * @param memberDetails
     * @param enterpriseProfileRequest
     * @return
     */
    @PutMapping("/enterprise/profile")
    public ResponseEntity<EnterpriseProfileResponse> coverEnterpriseIntroduce(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody EnterpriseProfileRequest enterpriseProfileRequest) {
        EnterpriseProfileResponse enterpriseProfileResponse = enterpriseService.updateIntro(memberDetails, enterpriseProfileRequest);
        return new ResponseEntity<>(enterpriseProfileResponse, HttpStatus.OK);
    }

    /**
     * 기업 대쉬보드 프로필 정보 조회
     * @param memberDetails
     * @return
     */
    @GetMapping("/enterprise-profile")
    public ResponseEntity<EnterpriseDashBoardProfileResponse> findDashBoardProfile(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {

        EnterpriseDashBoardProfileResponse dashBoardProfile = enterpriseService.findDashBoardProfile(memberDetails);

        return new ResponseEntity<>(dashBoardProfile, HttpStatus.OK);
    }

    @GetMapping("/wish-freelancer")
    public ResponseEntity<List<FreelancerSimpleResponse>> findWishFreelancer(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        enterpriseService.findWishFreelancer(memberDetails);
        return null;
    }

}
