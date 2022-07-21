package com.example.elancer.project.model;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.model.enterpriseintro.EnterpriseMainBiz;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.member.domain.Address;
import com.example.elancer.waitproject.model.WaitProject;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;
    @Enumerated(EnumType.STRING)
    private ProjectBackGround projectBackGround;
    @Enumerated(EnumType.STRING)
    private ProjectStep projectStep;
    private String mainBiz;
    @Enumerated(EnumType.STRING)
    private PositionKind positionKind;
    private String skill;
    private String projectName;
    private Integer headCount;
    private Integer inputHeadCount;
    private String content;
    private LocalDate projectStateDate;
    private LocalDate projectEndDate;
    private LocalDate recruitEndDate;
    private Address address;
    private Integer minMoney;
    private Integer maxMoney;
    private Integer careerYear;
    private Integer careerMonth;
    private Integer minDesiredAge;
    private Integer maxDesiredAge;
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
    // todo - 요구사항 정의서 나중에 추가

    @ManyToOne(fetch = FetchType.LAZY)
    private Enterprise enterprise;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<WaitProject> waitProjects = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<InterviewProject> interviewProjects = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ApplyProject> applyProjects = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnterpriseLogo enterpriseLogo;

    @Builder
    public Project(ProjectType projectType,
                   ProjectBackGround projectBackGround,
                   ProjectStep projectStep,
                   String mainBiz,
                   PositionKind positionKind,
                   String skill,
                   String projectName,
                   Integer headCount,
                   Integer inputHeadCount,
                   String content,
                   LocalDate projectStateDate,
                   LocalDate projectEndDate,
                   LocalDate recruitEndDate,
                   Address address,
                   Integer minMoney,
                   Integer maxMoney,
                   Integer careerYear,
                   Integer careerMonth,
                   Integer minDesiredAge,
                   Integer maxDesiredAge,
                   ProjectStatus projectStatus,
                   Enterprise enterprise
    ) {
        this.projectType = projectType;
        this.projectBackGround = projectBackGround;
        this.projectStep = projectStep;
        this.mainBiz = mainBiz;
        this.positionKind = positionKind;
        this.skill = skill;
        this.projectName = projectName;
        this.headCount = headCount;
        this.inputHeadCount = inputHeadCount;
        this.content = content;
        this.projectStateDate = projectStateDate;
        this.projectEndDate = projectEndDate;
        this.recruitEndDate = recruitEndDate;
        this.address = address;
        this.minMoney = minMoney;
        this.maxMoney = maxMoney;
        this.careerYear = careerYear;
        this.careerMonth = careerMonth;
        this.minDesiredAge = minDesiredAge;
        this.maxDesiredAge = maxDesiredAge;
        this.projectStatus = projectStatus;
        this.enterprise = enterprise;
    }

    public void changeProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<String> skillListConverter() {
        return Arrays.stream(skill.split(",")).map(s -> s.trim()).collect(Collectors.toList());
    }

    public FreelancerWorkmanShip careerToWorkmanshipConverter() {
        if (5L > careerYear) {
            return FreelancerWorkmanShip.JUNIOR;
        } else if (10L > careerYear) {
            return FreelancerWorkmanShip.MIDDLE;
        } else {
            return FreelancerWorkmanShip.SENIOR;
        }
    }

    public String payConverter() {
        if (minMoney == 0 && maxMoney == 0) {
            return "비공개";
        } else if (minMoney == 0 && maxMoney != 0) {
            return "협의가능";
        } else {
            return maxMoney.toString();
        }
    }

    public String demandCareer() {
        if (careerYear == 0 && careerMonth == 0) {
            return "무관";
        } else if (5L > careerYear) {
            return FreelancerWorkmanShip.JUNIOR.getDesc();
        } else if (10L > careerYear) {
            return FreelancerWorkmanShip.MIDDLE.getDesc();
        } else {
            return FreelancerWorkmanShip.SENIOR.getDesc();
        }
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }


}

