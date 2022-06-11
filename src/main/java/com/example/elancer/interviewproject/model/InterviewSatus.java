package com.example.elancer.interviewproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InterviewSatus {
    WAITING("대기"),
    ACCEPT("수락");

    private String desc;
}
