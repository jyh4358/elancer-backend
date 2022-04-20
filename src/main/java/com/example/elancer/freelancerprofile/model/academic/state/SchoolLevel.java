package com.example.elancer.freelancerprofile.model.academic.state;

import lombok.Getter;

@Getter
public enum SchoolLevel {
    HIGH_SCHOOL("고등학교"),
    COLLEGE("대학교(2,3년)"),
    UNIVERSITY("대학교(4년)"),
    MASTER_DEGREE("대학원(석사)"),
    DOCTORAL_DEGREE("대학원(박사)")
    ;

    private String desc;

    SchoolLevel(String desc) {
        this.desc = desc;
    }
}
