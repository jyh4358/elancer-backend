package com.example.elancer.freelancerprofile.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FreelancerProfileSimpleResponses {
    private List<FreelancerProfileSimpleResponse> freelancerProfileSimpleResponses;

    public FreelancerProfileSimpleResponses(List<FreelancerProfileSimpleResponse> freelancerProfileSimpleResponses) {
        this.freelancerProfileSimpleResponses = freelancerProfileSimpleResponses;
    }
}
