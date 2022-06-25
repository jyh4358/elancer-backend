package com.example.elancer.waitproject.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WithdrawFreelancerRequest {
    private Long projectNum;
    private Long freelancerNum;
}
