package com.example.elancer;


import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.login.auth.dto.OAuthAttributes;
import com.example.elancer.member.domain.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeTestController {

    @RequestMapping("/index")
    public String home() {
        return "home";
    }

    @GetMapping("/member")
    public ResponseEntity<MemberDetails> member(@AuthenticationPrincipal MemberDetails request) {
        log.info("member memberDetails = {}", request);

        return ResponseEntity.ok().body(request);
    }

    @GetMapping("/signup")
    public ResponseEntity<OAuthAttributes> signUp(@AuthenticationPrincipal MemberDetails request,
                                                @AuthenticationPrincipal OAuthAttributes oAuthAttributes) {
        log.info("signup memberDetails = {}", request);
        log.info("signup OAuthAttributes = {}", oAuthAttributes);
        return ResponseEntity.ok().body(oAuthAttributes);
    }

    @GetMapping("/signup-enterprise")
    public ResponseEntity<OAuthAttributes> signupEnterpriseForm(@AuthenticationPrincipal MemberDetails request,
                                                              @AuthenticationPrincipal OAuthAttributes oAuthAttributes) {
        log.info("signupEnterpriseForm memberDetails = {}", request);
        log.info("signup OAuthAttributes = {}", oAuthAttributes);

        oAuthAttributes.setRole(MemberType.ENTERPRISE);
        oAuthAttributes.getAuthorities();

        return ResponseEntity.ok().body(oAuthAttributes);

    }

    @GetMapping("/signup-freelancer")
    public ResponseEntity<OAuthAttributes> signupFreelancerForm(@AuthenticationPrincipal MemberDetails request,
                                                              @AuthenticationPrincipal OAuthAttributes oAuthAttributes) {
        log.info("signupEnterpriseForm memberDetails = {}", request);
        log.info("signup OAuthAttributes = {}", oAuthAttributes);

        oAuthAttributes.setRole(MemberType.FREELANCER);
        oAuthAttributes.getAuthorities();

        return ResponseEntity.ok().body(oAuthAttributes);
    }



    @PreAuthorize("hasRole('ROLE_ENTERPRISE')")
    @GetMapping("/dashboard-enterprise")
    public ResponseEntity<OAuthAttributes> dashboardEnterprise(@AuthenticationPrincipal MemberDetails request,
                                                                @AuthenticationPrincipal OAuthAttributes oAuthAttributes) {
        log.info("dashboardEnterprise memberDetails = {}", request);
        log.info("dashboardEnterprise OAuthAttributes = {}", oAuthAttributes);

        return ResponseEntity.ok().body(oAuthAttributes);
    }


}
