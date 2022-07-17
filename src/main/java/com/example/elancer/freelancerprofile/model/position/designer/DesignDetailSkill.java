package com.example.elancer.freelancerprofile.model.position.designer;

import lombok.Getter;

@Getter
public enum DesignDetailSkill {
    AFEREEFFECT("Afere Effect"),
    ILLUSTRATOR("Illustrator"),
    INDESIGN("InDesign"),
    PHOTOSHOP("Photoshop"),
    SKETCH("Sketch"),
    ZEPLIN("Zeplin"),
    ADOBE_XD("Adobe XD"),
    THREEDMAXANDMAYA("3D Max/Maya"),
    CLIPSTUDIO("CLIP STUDIO"),
    MEDIBANG("MediBang"),
    FLASH("Flash")
    ;
    private String desc;

    DesignDetailSkill(String desc) {
        this.desc = desc;
    }
}
