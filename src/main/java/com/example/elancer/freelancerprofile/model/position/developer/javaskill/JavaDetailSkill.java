package com.example.elancer.freelancerprofile.model.position.developer.javaskill;

import lombok.Getter;

@Getter
public enum JavaDetailSkill {
    FRONT_END("Front-End"),
    BACK_END("Back-End"),
    SPRING("Spring"),
    XPLATFORM("Xplatform"),
    MIPLATFORM("Miplatform"),
    NEXACRO("Nexacro"),
    PROFRAME("Proframe"),
    MAVEN("Maven"),
    JENKINS("Jenkins"),
    SENCHA("Sencha"),
    TRUSTFORM("Trustform"),
    TUXEDO("Tuxedo"),
    GAUCE("Gauce"),
    PRO_C("Pro*C"),
    DEVON("DevOn"),
    THYMELEAF("Thymeleaf")
    ;

    private String desc;

    JavaDetailSkill(String desc) {
        this.desc = desc;
    }
}
