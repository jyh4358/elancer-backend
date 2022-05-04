package com.example.elancer.freelancer.model;

public enum WorkPossibleState {
    POSSIBLE("가능"),
    IMPOSSIBLE("불가능")
    ;

    private String desc;

    WorkPossibleState(String desc) {
        this.desc = desc;
    }
}
