package com.example.elancer.freelancerprofile.model.position;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum PositionType {
    DEVELOPER("개발자", PositionFactory::generateDeveloper),
    PUBLISHER("퍼블리셔", PositionFactory::generatePublisher),
    DESIGNER("디자이너", PositionFactory::generateDesigner),
    PLANNER("기획자", PositionFactory::generatePlanner),
    CROWD_WORKER("크라우드워커", PositionFactory::generateCrowdWorker),
    ETC("기타", PositionFactory::generatePositionEtc)
    ;

    private String desc;
    private Function<FreelancerProfile, Position> function;

}
