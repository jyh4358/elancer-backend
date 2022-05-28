package com.example.elancer.freelancerprofile.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FreelancerSimpleResponses {
    private List<FreelancerSimpleResponse> freelancerSimpleResponseList;
}
