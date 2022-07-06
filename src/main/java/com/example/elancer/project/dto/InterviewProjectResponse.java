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
public class InterviewProjectResponse {
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
    private int interviewFreelancerCount;
    private List<InterviewFreelancerDto> interviewFreelancerList = new ArrayList<>();
    private List<ApplicantDto> applyFreelancerList = new ArrayList<>();

    @Builder
    public InterviewProjectResponse(Long projectNum,
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
                                    int interviewFreelancerCount,
                                    List<InterviewFreelancerDto> interviewFreelancerList,
                                    List<ApplicantDto> applyFreelancerList
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
        this.applyFreelancerCount = applyFreelancerCount;
        this.interviewFreelancerCount = interviewFreelancerCount;
        this.interviewFreelancerList = interviewFreelancerList;
        this.applyFreelancerList = applyFreelancerList;
    }

    public static InterviewProjectResponse of(Project project,
                                              int applyFreelancerCount,
                                              int interviewFreelancerCount,
                                              List<ApplicantDto> applyFreelancerList,
                                              List<InterviewFreelancerDto> interviewFreelancerList
    ) {
        return InterviewProjectResponse.builder()
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
                .interviewFreelancerCount(interviewFreelancerCount)
                .applyFreelancerList(applyFreelancerList)
                .interviewFreelancerList(interviewFreelancerList)
                .build();
    }

}
