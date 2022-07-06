package com.example.elancer.freelancer.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerObtainOrdersResponse {
    private int applyProjectCount;
    private int InterviewProjectCount;
    private int JoinedProjectCount;
    private int wishProjectCount;

}
