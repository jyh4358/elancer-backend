package com.example.elancer.freelancer.model;

import lombok.Getter;

@Getter
public enum KOSAState {
    POSSESS("있다"),
    NOT_POSSESS("없다")
    ;

    private String desc;

    KOSAState(String desc) {
        this.desc = desc;
    }
}
