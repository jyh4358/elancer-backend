package com.example.elancer.interviewproject.dto;

import com.example.elancer.interviewproject.model.InterviewStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InterviewProjectResponse {
    private Long num;
    private String name;
    private String Phone;
    private InterviewStatus interviewStatus;
}
