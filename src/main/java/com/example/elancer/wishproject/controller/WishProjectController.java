package com.example.elancer.wishproject.controller;

import com.example.elancer.freelancerprofile.dto.request.position.DeveloperCoverRequest;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishproject.dto.WishProjectSaveRequest;
import com.example.elancer.wishproject.model.WishProject;
import com.example.elancer.wishproject.service.WishProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WishProjectController {
    private final WishProjectService wishProjectService;

    @PostMapping(WishProjectControllerPath.WISH_PROJECT_SAVE)
    public ResponseEntity<Void> saveWishProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody WishProjectSaveRequest wishProjectSaveRequest
    ) {
        wishProjectService.saveWishProject(memberDetails, wishProjectSaveRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
