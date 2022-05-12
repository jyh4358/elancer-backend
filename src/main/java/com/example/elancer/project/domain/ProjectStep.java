package com.example.elancer.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectStep {
    ANALYSIS("분석/설계"),
    PLAN("기획"),
    DESIGN("디자인"),
    PUBLISHING("퍼블리싱"),
    DEVELOP("개발"),
    OPERATION("운영중");

    private String desc;

}
