package com.example.elancer.freelancerprofile.model.position.designer;

import lombok.Getter;

@Getter
public enum DesignDetailSkill {
    AFERE_EFFECT("Afere Effect"),
    ILLUSTRATOR("Illustrator"),
    INDESIGN("InDesign"),
    PHOTOSHOP("Photoshop"),
    SKETCH("Sketch"),
    ZEPLIN("Zeplin"),
    ADOBE_XD("Adobe XD"),
    THREE_D_MAX_AND_MAYA("3D Max/Maya"),
    CLIP_STUDIO("CLIP STUDIO"),
    MEDIBANG("MediBang"),
    FLASH("Flash")
    ;
    private String desc;

    DesignDetailSkill(String desc) {
        this.desc = desc;
    }
}
