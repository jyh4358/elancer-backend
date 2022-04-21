package com.example.elancer.freelancerprofile.model.language;

import lombok.Getter;

@Getter
public enum LanguageAbility {
    SEMI("일상회화 가능"),
    MIDDLE("비즈니스 회화가능"),
    NATIVE("원어민 수준")
    ;

    private String desc;

    LanguageAbility(String desc) {
        this.desc = desc;
    }
}
