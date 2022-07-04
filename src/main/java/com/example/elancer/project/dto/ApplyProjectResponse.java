package com.example.elancer.project.dto;

import com.example.elancer.project.model.PositionKind;
import com.example.elancer.project.model.Project;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyProjectResponse {
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
    private int applyFreelancerCount;
    private List<ApplicantDto> applyFreelancerList = new ArrayList<>();

    @Builder
    public ApplyProjectResponse(Long projectNum,
                               String projectName,
                               PositionKind positionKind,
                               String demandCareer,
                               int headCount,
                               LocalDate projectStateDate,
                               LocalDate projectEndDate,
                               int minMoney,
                               int maxMoney,
                               LocalDate createdDate,
                               int applyFreelancerCount,
                               List<ApplicantDto> applyFreelancerList) {
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
        this.applyFreelancerCount = applyFreelancerCount;
        this.applyFreelancerList = applyFreelancerList;
    }

    public static ApplyProjectResponse of(Project project,
                                         int applyFreelancerCount,
                                         List<ApplicantDto> applyFreelancerList) {
        return ApplyProjectResponse.builder()
                .projectNum(project.getNum())
                .projectName(project.getProjectName())
                .positionKind(project.getPositionKind())
                .demandCareer(project.demandCareer())
                .headCount(project.getHeadCount())
                .projectStateDate(project.getProjectStateDate())
                .projectEndDate(project.getProjectEndDate())
                .minMoney(project.getMinMoney())
                .maxMoney(project.getMaxMoney())
                .createdDate(LocalDate.from(project.getCreatedDate()))
                .applyFreelancerCount(applyFreelancerCount)
                .applyFreelancerList(applyFreelancerList)
                .build();
    }
}
