package com.example.elancer.project.model;

import lombok.Getter;

@Getter
public enum FreelancerWorkmanShip {
    JUNIOR("초급"),
    MIDDLE("중급"),
    SENIOR("고급");

    private final String desc;

    FreelancerWorkmanShip(String desc) {
        this.desc = desc;
    }
}
