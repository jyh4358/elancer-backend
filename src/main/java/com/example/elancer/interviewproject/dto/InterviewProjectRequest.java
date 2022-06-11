package com.example.elancer.interviewproject.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InterviewProjectRequest {
    @NotNull
    private Long projectNum;

    @NotNull
    private Long freelancerNum;
}
