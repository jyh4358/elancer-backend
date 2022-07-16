package com.example.elancer.wishfreelancer.controller;

import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishfreelancer.service.WishFreelancerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WishFreelancerController {

    private WishFreelancerService wishFreelancerService;


    @PostMapping("/wish-freelancer/{freelancerNum}")
    public ResponseEntity<Void> addWishFreelancer(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long freelancerNum

    ) {
        wishFreelancerService.addWishFreelancer(memberDetails, freelancerNum);
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
