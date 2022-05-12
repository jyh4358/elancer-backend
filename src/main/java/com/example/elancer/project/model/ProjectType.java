package com.example.elancer.project.model;

public enum ProjectType {

    TELEWORKING("재택"),
    WORKING("상주");

    private String desc;

    ProjectType(String desc) {
        this.desc = desc;
    }
}
