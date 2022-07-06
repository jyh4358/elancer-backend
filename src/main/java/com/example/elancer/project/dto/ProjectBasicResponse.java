package com.example.elancer.project.dto;

import com.example.elancer.project.model.PositionKind;
import com.example.elancer.project.model.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectBasicResponse {
    private Long projectNum;
    private String projectName;
    private PositionKind positionKind;
    private String demandCareer;
    private int headCount;
    private LocalDate projectStateDate;
    private LocalDate projectEndDate;
    private int minMoney;
    private int maxMoney;
    private LocalDate createdDate;

    private ProjectBasicResponse(
            Long projectNum,
            String projectName,
            PositionKind positionKind,
            String demandCareer,
            int headCount,
            LocalDate projectStateDate,
            LocalDate projectEndDate,
            int minMoney,
            int maxMoney,
            LocalDate createdDate
    ) {
        this.projectNum = projectNum;
        this.projectName = projectName;
        this.positionKind = positionKind;
        this.demandCareer = demandCareer;
        this.headCount = headCount;
        this.projectStateDate = projectStateDate;
        this.projectEndDate = projectEndDate;
        this.minMoney = minMoney;
        this.maxMoney = maxMoney;
        this.createdDate = createdDate;
    }

    public static ProjectBasicResponse of(Project project) {
        return new ProjectBasicResponse(
                project.getNum(),
                project.getProjectName(),
                project.getPositionKind(),
                project.demandCareer(),
                project.getHeadCount(),
                project.getProjectStateDate(),
                project.getProjectEndDate(),
                project.getMinMoney(),
                project.getMaxMoney(),
                project.getCreatedDate().toLocalDate()
        );
    }

    public static List<ProjectBasicResponse> listOf(List<Project> projects) {
        return projects.stream()
                .map(ProjectBasicResponse::of)
                .collect(Collectors.toList());
    }
}
