package com.example.elancer.project.dto;

import com.example.elancer.member.domain.Address;
import com.example.elancer.project.model.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectSaveRequest {
    private ProjectType projectType;
    private ProjectBackGround projectBackGround;
    private EnterpriseLogo enterpriseLogo;
    private ProjectStep projectStep;
    private String mainBiz;
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
    // todo - 요구사항 정의서 나중에 추가

    private String companyName;
    private String name;
    private String position;
    private String phone;
    private String telNumber;
    private String email;

    public Project toEntity() {
        return Project.builder()
                .projectType(projectType)
                .projectBackGround(projectBackGround)
                .enterpriseLogo(enterpriseLogo)
                .projectStep(projectStep)
                .mainBiz(mainBiz)
                .positionKind(positionKind)
                .skill(skill)
                .projectName(projectName)
                .headCount(headCount)
                .inputHeadCount(inputHeadCount)
                .content(content)
                .projectStateDate(projectStateDate)
                .projectEndDate(projectEndDate)
                .recruitEndDate(recruitEndDate)
                .address(address)
                .minMoney(minMoney)
                .maxMoney(maxMoney)
                .careerYear(careerYear)
                .careerMonth(careerMonth)
                .minDesiredAge(minDesiredAge)
                .maxDesiredAge(maxDesiredAge)
                .build();
    }
}
