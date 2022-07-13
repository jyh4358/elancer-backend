package com.example.elancer.project.dto;

import com.example.elancer.member.domain.Address;
import com.example.elancer.project.model.FreelancerWorkmanShip;
import com.example.elancer.project.model.Project;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Array;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectDetailResponse {
    private String projectName;
    private String pay;
    private FreelancerWorkmanShip freelancerWorkmanShip;
    private Long projectPeriod;
    private Address address;
    private List<String> skills = new ArrayList<>();
    private int headCount;
    private int inputHeadCount;
    private String content;
    private List<SimpleFreelancerDto> simpleFreelancerList = new ArrayList<>();


    @Builder
    public ProjectDetailResponse(String projectName,
                                 String pay,
                                 FreelancerWorkmanShip freelancerWorkmanShip,
                                 Long projectPeriod, Address address,
                                 List<String> skills,
                                 int headCount,
                                 int inputHeadCount,
                                 String content,
                                 List<SimpleFreelancerDto> simpleFreelancerList
    ) {
        this.projectName = projectName;
        this.pay = pay;
        this.freelancerWorkmanShip = freelancerWorkmanShip;
        this.projectPeriod = projectPeriod;
        this.address = address;
        this.skills = skills;
        this.headCount = headCount;
        this.inputHeadCount = inputHeadCount;
        this.content = content;
        this.simpleFreelancerList = simpleFreelancerList;
    }

    public static ProjectDetailResponse of(
            Project project,
            List<SimpleFreelancerDto> simpleFreelancerList
    ) {
        return ProjectDetailResponse.builder()
                .projectName(project.getProjectName())
                .pay(project.payConverter())
                .freelancerWorkmanShip(project.careerToWorkmanshipConverter())
                .projectPeriod(ChronoUnit.MONTHS.between(project.getProjectStateDate(), project.getProjectEndDate()))
                .address(project.getAddress())
                .skills(project.skillListConverter())
                .headCount(project.getHeadCount())
                .inputHeadCount(project.getInputHeadCount())
                .content(project.getContent())
                .simpleFreelancerList(simpleFreelancerList)
                .build();
    }
}
