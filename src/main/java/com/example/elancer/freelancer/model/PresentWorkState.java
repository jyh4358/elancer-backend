package com.example.elancer.freelancer.model;

import lombok.Getter;

@Getter
public enum PresentWorkState {
    JOB_HUNTING("구직중"),
    REGULAR("정규직[재직중]"),
    FREE_AT_COMPANY("프리랜서[상주]"),
    FREE_AT_HOME("프리랜서[재택]"),
    ;

    private String desc;

    PresentWorkState(String desc) {
        this.desc = desc;
    }
}
