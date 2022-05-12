package com.example.elancer.project.domain;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.member.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "project")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class project extends BasicEntity {

    private ProjectType projectType;
    private String bgColor;
    private EnterpriseLogo enterpriseLogo;
    private ProjectStep projectStep;
    private String mainBiz;
    private PositionType positionType;
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
    // todo - 요구사항 정의서 나중에 추가

    private String companyName;
    private String name;
    private String position;
    private String phone;
    private String telNumber;
    private String email;

    public project(ProjectType projectType,
                   String bgColor,
                   EnterpriseLogo enterpriseLogo,
                   ProjectStep projectStep,
                   String mainBiz,
                   PositionType positionType,
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
                   String companyName,
                   String name,
                   String position,
                   String phone,
                   String telNumber,
                   String email) {
        this.projectType = projectType;
        this.bgColor = bgColor;
        this.enterpriseLogo = enterpriseLogo;
        this.projectStep = projectStep;
        this.mainBiz = mainBiz;
        this.positionType = positionType;
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
        this.companyName = companyName;
        this.name = name;
        this.position = position;
        this.phone = phone;
        this.telNumber = telNumber;
        this.email = email;
    }
}
