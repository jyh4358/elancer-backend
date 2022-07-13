package com.example.elancer.project.model;

public enum ProjectType {

    TELEWORKING("재택"),
    WORKING("상주"),
    BOTH_TELEWORKING_WORKING("재택,상주");

    private String desc;

    ProjectType(String desc) {
        this.desc = desc;
    }
}
