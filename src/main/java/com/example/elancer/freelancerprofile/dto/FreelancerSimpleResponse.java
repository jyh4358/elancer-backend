package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import lombok.AccessLevel;
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
    private List<String> projectNames;

    public FreelancerSimpleResponse(
            Long freelancerNum,
            String positionName,
            String freelancerName,
            IntroBackGround introBackGround,
            int careerYear,
            String greeting,
            List<String> skills,
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
        this.projectNames = projectNames;
    }

    public static FreelancerSimpleResponse of(Position position) {
        return new FreelancerSimpleResponse(
                position.getFreelancerProfile().getFreelancer().getNum(),
                position.getPositionType().getDesc(),
                position.getFreelancerProfile().getFreelancer().getName(),
                position.getFreelancerProfile().getIntroBackGround(),
                position.getFreelancerProfile().getFreelancer().getFreelancerAccountInfo().getCareerYear(),
                position.getFreelancerProfile().getGreeting(),
                position.getAllSkillNames(),
                position.getFreelancerProfile().getProjectHistoryNames()
        );
    }

    public void switchWishState() {
        this.wishState = true;
    }

}
