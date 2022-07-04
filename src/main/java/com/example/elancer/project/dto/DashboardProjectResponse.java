package com.example.elancer.project.dto;

import com.example.elancer.project.model.PositionKind;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectStatus;
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
    private ProjectStatus projectStatus;
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
    private int waitCount;
    private int workCount;
    private List<ApplicantDto> applyFreelancerList = new ArrayList<>();
    private List<InterviewFreelancerDto> interviewFreelancerList = new ArrayList<>();
    private List<WaitFreelancerDto> waitFreelancerList = new ArrayList<>();
    private List<WaitFreelancerDto> workFreelancerList = new ArrayList<>();

    @Builder
    public DashboardProjectResponse(Long projectNum,
                                    String projectName,
                                    ProjectStatus projectStatus,
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
                                    int waitCount,
                                    int workCount,
                                    List<ApplicantDto> applyFreelancerList,
                                    List<InterviewFreelancerDto> interviewFreelancerList,
                                    List<WaitFreelancerDto> waitFreelancerList,
                                    List<WaitFreelancerDto> workFreelancerList
    ) {
        this.projectNum = projectNum;
        this.projectName = projectName;
        this.projectStatus = projectStatus;
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
        this.waitCount = waitCount;
        this.workCount = workCount;
        this.applyFreelancerList = applyFreelancerList;
        this.interviewFreelancerList = interviewFreelancerList;
        this.waitFreelancerList = waitFreelancerList;
        this.workFreelancerList = workFreelancerList;
    }

    public static DashboardProjectResponse of(Project project,
                                              int applyCount,
                                              int interviewCount,
                                              int waitCount,
                                              int workCount,
                                              List<ApplicantDto> applyFreelancerList,
                                              List<InterviewFreelancerDto> interviewFreelancerList,
                                              List<WaitFreelancerDto> waitFreelancerList,
                                              List<WaitFreelancerDto> workFreelancerList
    ) {
        return DashboardProjectResponse.builder()
                .projectNum(project.getNum())
                .projectName(project.getProjectName())
                .projectStatus(project.getProjectStatus())
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
                .waitCount(waitCount)
                .workCount(workCount)
                .applyFreelancerList(applyFreelancerList)
                .interviewFreelancerList(interviewFreelancerList)
                .waitFreelancerList(waitFreelancerList)
                .workFreelancerList(workFreelancerList)
                .build();
    }
}
