package com.example.elancer.freelancer.model;

import lombok.Getter;

@Getter
public enum IntroBackGround {
    WHITE("화이트"),
    COBALT_BLUE("코발트블루"),
    LIGHT_PURPLE("밝은 보라"),
    DARK_PINK("어두운핑크"),
    DARK_ORANGE("어두운주황"),
    ORANGE("주황"),
    LIGHT_ORANGE("밝은 주황"),
    PATTERN1("패턴1"),
    PATTERN2("패턴2"),
    PATTERN3("패턴3"),
    PATTERN4("패턴4"),
    PATTERN5("패턴5")
    ;

    private String desc;

    IntroBackGround(String desc) {
        this.desc = desc;
    }
}
