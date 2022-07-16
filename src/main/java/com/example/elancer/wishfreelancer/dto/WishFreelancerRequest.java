package com.example.elancer.wishfreelancer.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishFreelancerRequest {
    private Long freelancerNum;

    public WishFreelancerRequest(Long freelancerNum) {
        this.freelancerNum = freelancerNum;
    }
}
