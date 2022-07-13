package com.example.elancer.project.model;

import lombok.Getter;

@Getter
public enum FreelancerWorkmanShip {
    JUNIOR("초급", 5),
    MIDDLE("중급", 10),
    SENIOR("고급", 15);

    private final String desc;
    private final int career;

    FreelancerWorkmanShip(String desc, int career) {
        this.desc = desc;
        this.career = career;

    }
}
