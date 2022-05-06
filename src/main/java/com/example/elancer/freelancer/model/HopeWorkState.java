package com.example.elancer.freelancer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum HopeWorkState {
    AT_COMPANY("상주"),
    AT_HALF_COMPANY("반상주"),
    AT_HOME("재택"),
    REGULAR("정규직"),
    NO_MATTER("관계없음")
    ;

    private String desc;

    HopeWorkState(String desc) {
        this.desc = desc;
    }
}
