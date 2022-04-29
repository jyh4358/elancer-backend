package com.example.elancer.freelancerprofile.model.position;

import com.example.elancer.freelancerprofile.dto.response.PositionResponse;
import lombok.Getter;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public enum PositionType {
    DEVELOPER("개발자"),
    PUBLISHER("퍼블리셔"),
    DESIGNER("디자이너"),
    PLANNER("기획자"),
    CROWD_WORKER("크라우드워커"),
    ETC("기타")
    ;

    private String desc;
//    private Function<Positionx>


    PositionType(String desc) {
        this.desc = desc;
    }
}
