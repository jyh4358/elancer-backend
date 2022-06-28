package com.example.elancer.waitproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WaitStatus {
    WAITING("대기"),
    WORKING("진행");

    private String desc;
}
