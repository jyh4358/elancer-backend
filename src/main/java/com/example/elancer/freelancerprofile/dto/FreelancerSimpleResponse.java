package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerSimpleResponse {
    private Long freelancerNum;
    private String positionName;
    private String freelancerName;
    private IntroBackGround introBackGround;
    private int careerYear;
    private boolean wishState;
    private String greeting;
    private List<String> skills;
    private String etcSkill;
    private List<String> projectNames;

    public FreelancerSimpleResponse(
            Long freelancerNum,
            String positionName,
            String freelancerName,
            IntroBackGround introBackGround,
            int careerYear,
            String greeting,
            List<String> skills,
            String etcSkill,
            List<String> projectNames
    ) {
        this.freelancerNum = freelancerNum;
        this.positionName = positionName;
        this.freelancerName = freelancerName;
        this.introBackGround = introBackGround;
        this.careerYear = careerYear;
        this.wishState = false;
        this.greeting = greeting;
        this.skills = skills;
        this.etcSkill = etcSkill;
        this.projectNames = projectNames;
    }


    public static FreelancerSimpleResponse of(Developer developer) {
        return new FreelancerSimpleResponse(
                developer.getFreelancerProfile().getFreelancer().getNum(),
                developer.getPositionType().getDesc(),
                developer.getFreelancerProfile().getFreelancer().getName(),
                developer.getFreelancerProfile().getIntroBackGround(),
                developer.getFreelancerProfile().getFreelancer().getFreelancerAccountInfo().getCareerYear(),
                developer.getFreelancerProfile().getGreeting(),
                developer.getAllSkillNames(),
                developer.getEtcSkill(),
                developer.getFreelancerProfile().getProjectHistoryNames()
        );
    }

    public static List<FreelancerSimpleResponse> listOf(List<Developer> developers) {
        return developers.stream()
                .map(FreelancerSimpleResponse::of)
                .collect(Collectors.toList());
    }

    public void switchWishState() {
        this.wishState = true;
    }

}
