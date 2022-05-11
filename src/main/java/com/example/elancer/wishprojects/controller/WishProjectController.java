package com.example.elancer.wishprojects.controller;

import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishprojects.dto.WishProjectDeleteRequest;
import com.example.elancer.wishprojects.dto.WishProjectSaveRequest;
import com.example.elancer.wishprojects.service.WishProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping(WishProjectControllerPath.WISH_PROJECT_DELETE)
    public ResponseEntity<Void> deleteWishProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody WishProjectDeleteRequest wishProjectDeleteRequest
    ) {
        wishProjectService.deleteWishProject(memberDetails, wishProjectDeleteRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
