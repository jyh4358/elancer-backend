package com.example.elancer.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectStatus {
    REGISTRATION("등록"),
    PROGRESS("진행"),
    COMPLETION("완료");

    private String desc;
}
