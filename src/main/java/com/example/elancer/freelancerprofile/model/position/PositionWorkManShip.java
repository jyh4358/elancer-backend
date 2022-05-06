package com.example.elancer.freelancerprofile.model.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
public enum PositionWorkManShip {
    JUNIOR("초급", 0, 4),
    MIDDLE("중급", 5, 9),
    SENIOR("고급", 10, null)
        ;

    private final String desc;
    private final Integer yearInLine;
    private final Integer yearOutLine;

    PositionWorkManShip(String desc, Integer yearInLine, Integer yearOutLine) {
        this.desc = desc;
        this.yearInLine = yearInLine;
        this.yearOutLine = yearOutLine;
    }
}
