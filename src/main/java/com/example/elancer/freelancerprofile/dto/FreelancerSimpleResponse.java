package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerSimpleResponse {
    private PositionType positionName;
    private String freelancerName;
    private IntroBackGround introBackGround;
    private int careerYear;
    private String greeting;
    private List<String> skills;
    private List<String> projectNames;

    @QueryProjection
    public FreelancerSimpleResponse(
            PositionType positionName,
            String freelancerName,
            IntroBackGround introBackGround,
            int careerYear,
            String greeting,
            List<String> skills,
            String etcSkill,
            List<String> projectNames
    ) {
        this.positionName = positionName;
        this.freelancerName = freelancerName;
        this.introBackGround = introBackGround;
        this.careerYear = careerYear;
        this.greeting = greeting;
        skills.add(etcSkill);
        this.skills = skills;
        this.projectNames = projectNames;
    }
}
