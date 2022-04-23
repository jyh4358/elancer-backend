package com.example.elancer.freelancerprofile.model.position.developer.cskill;

import lombok.Getter;

@Getter
public enum CDetailSkill {
    SERVER("Server"),
    UNIX("Unix"),
    EMBEDDED("Embedded"),
    FIRMWARE("Firmware"),
    MACHINE_VISION("Machine Vision"),
    ADUINO("Aduino"),
    QT("Qt"),
    METALAB("MetaLab"),
    LABVIEW("LabVIEW"),
    ;

    private String desc;

    CDetailSkill(String desc) {
        this.desc = desc;
    }
}
