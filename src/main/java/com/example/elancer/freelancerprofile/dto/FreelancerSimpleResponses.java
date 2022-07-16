package com.example.elancer.freelancerprofile.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerSimpleResponses {
    private List<FreelancerSimpleResponse> freelancerSimpleResponseList;
    private boolean hasNext;

    public FreelancerSimpleResponses(List<FreelancerSimpleResponse> freelancerSimpleResponseList, boolean hasNext) {
        this.freelancerSimpleResponseList = freelancerSimpleResponseList;
        this.hasNext = hasNext;
    }
}
