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
public class DashboardProjectResponse {

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
    private int applyCount;
    private int interviewCount;
    private List<ApplicantDto> applicantList = new ArrayList<>();
    private List<InterviewRequestDto> interviewRequestList = new ArrayList<>();

    @Builder
    public DashboardProjectResponse(Long projectNum,
                                    String projectName,
                                    PositionKind positionKind,
                                    String demandCareer,
                                    int headCount,
                                    LocalDate projectStateDate,
                                    LocalDate projectEndDate,
                                    int minMoney,
                                    int maxMoney,
                                    LocalDate createdDate,
                                    int applyCount,
                                    int interviewCount,
                                    List<ApplicantDto> applicantList, List<InterviewRequestDto> interviewRequestList) {
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
        this.applyCount = applyCount;
        this.interviewCount = interviewCount;
        this.applicantList = applicantList;
        this.interviewRequestList = interviewRequestList;
    }

    public static DashboardProjectResponse of(Project project,
                                              int applyCount,
                                              int interviewCount,
                                              List<ApplicantDto> applicantList,
                                              List<InterviewRequestDto> interviewRequestList) {
        return DashboardProjectResponse.builder()
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
                .applyCount(applyCount)
                .interviewCount(interviewCount)
                .applicantList(applicantList)
                .interviewRequestList(interviewRequestList)
                .build();
    }
}
