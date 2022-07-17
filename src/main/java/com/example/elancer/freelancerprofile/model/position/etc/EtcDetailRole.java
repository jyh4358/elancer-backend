package com.example.elancer.freelancerprofile.model.position.etc;

import lombok.Getter;

@Getter
public enum EtcDetailRole {
    PM("PM"),
    PL("PL"),
    SYSTEMANALYSISANDPLAN("시스템분석/설계"),
    DA("DA"),
    DBA("DBA"),
    TA("TA"),
    AA("AA"),
    NA("NA"),
    PMO("PMO"),
    SE("SE"),
    QA("QA"),
    QC("QC")
    ;

    private String desc;

    EtcDetailRole(String desc) {
        this.desc = desc;
    }
}
