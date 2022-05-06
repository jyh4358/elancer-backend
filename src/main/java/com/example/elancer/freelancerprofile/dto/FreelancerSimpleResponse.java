package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancer.model.IntroBackGround;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerSimpleResponse {
    private String positionName;
    private String freelancerName;
    private IntroBackGround introBackGround;
    private int careerYear;
    private String greeting;
    private List<String> skills;
    private List<String> projectNames;

    @QueryProjection
    public FreelancerSimpleResponse(String positionName, String freelancerName, IntroBackGround introBackGround, int careerYear, String greeting, List<String> skills, List<String> projectNames) {
        this.positionName = positionName;
        this.freelancerName = freelancerName;
        this.introBackGround = introBackGround;
        this.careerYear = careerYear;
        this.greeting = greeting;
        this.skills = skills;
        this.projectNames = projectNames;
    }
}
