package com.example.elancer.wishfreelancer.controller;

import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishfreelancer.dto.WishFreelancerRequest;
import com.example.elancer.wishfreelancer.service.WishFreelancerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class WishFreelancerController {

    private WishFreelancerService wishFreelancerService;


    @PostMapping("/wish-freelancer")
    public ResponseEntity<Void> addWishFreelancer(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Valid @RequestBody WishFreelancerRequest wishFreelancerRequest
            ) {
        wishFreelancerService.addWishFreelancer(memberDetails, wishFreelancerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/wish-freelancer/{freelancerNum}")
    public ResponseEntity<Void> deleteWithProject(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long freelancerNum
    ) {

        wishFreelancerService.deleteWishFreelancer(memberDetails, freelancerNum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
