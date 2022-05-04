package com.example.elancer.freelancerprofile.model.projecthistory;

import lombok.Getter;

@Getter
public enum DevelopField {
    INDUSTRY("산업"),
    APPLICATION("응용")
    ;

    private String desc;

    DevelopField(String desc) {
        this.desc = desc;
    }
}
