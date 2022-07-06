package com.example.elancer.project.dto;

import com.example.elancer.member.domain.Address;
import com.example.elancer.project.model.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectBoxResponse {

    private Long projectNum;

    private ProjectType projectType;

    private ProjectBackGround projectBackGround;

    private PositionKind positionKind;

    private Long endDays;

    private List<String> skills = new ArrayList<>();

    private String projectName;

    private FreelancerWorkmanShip freelancerWorkmanShip;

    private Long projectPeriod;

    private Address address;

    private String pay;

    private String content;

    @Builder
    public ProjectBoxResponse(
            Long projectNum,
            ProjectType projectType,
            ProjectBackGround projectBackGround,
            PositionKind positionKind,
            Long endDays,
            List<String> skills,
            String projectName,
            FreelancerWorkmanShip freelancerWorkmanShip,
            Long projectPeriod,
            Address address,
            String pay,
            String content
    ) {
        this.projectNum = projectNum;
        this.projectType = projectType;
        this.projectBackGround = projectBackGround;
        this.positionKind = positionKind;
        this.endDays = endDays;
        this.skills = skills;
        this.projectName = projectName;
        this.freelancerWorkmanShip = freelancerWorkmanShip;
        this.projectPeriod = projectPeriod;
        this.address = address;
        this.pay = pay;
        this.content = content;
    }

    public static ProjectBoxResponse cardBoxOf(Project project) {
        return ProjectBoxResponse.builder()
                .projectNum(project.getNum())
                .projectType(project.getProjectType())
                .projectBackGround(project.getProjectBackGround())
                .positionKind(project.getPositionKind())
                .endDays(ChronoUnit.DAYS.between(LocalDate.now(), project.getProjectEndDate()))
                .skills(project.skillListConverter())
                .projectName(project.getProjectName())
                .freelancerWorkmanShip(project.careerToWorkmanshipConverter())
                .projectPeriod(ChronoUnit.MONTHS.between(project.getProjectStateDate(), project.getProjectEndDate()))
                .address(project.getAddress())
                .pay(project.payConverter())
                .build();
    }

    public static ProjectBoxResponse listBoxOf(Project project) {
        return ProjectBoxResponse.builder()
                .projectNum(project.getNum())
                .projectType(project.getProjectType())
                .projectBackGround(project.getProjectBackGround())
                .positionKind(project.getPositionKind())
                .endDays(ChronoUnit.DAYS.between(LocalDate.now(), project.getProjectEndDate()))
                .skills(project.skillListConverter())
                .projectName(project.getProjectName())
                .freelancerWorkmanShip(project.careerToWorkmanshipConverter())
                .projectPeriod(ChronoUnit.MONTHS.between(project.getProjectStateDate(), project.getProjectEndDate()))
                .address(project.getAddress())
                .pay(project.payConverter())
                .content(project.getContent())
                .build();
    }

}
