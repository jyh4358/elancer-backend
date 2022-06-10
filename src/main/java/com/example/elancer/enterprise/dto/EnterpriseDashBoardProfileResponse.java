package com.example.elancer.enterprise.dto;


import com.example.elancer.enterprise.model.enterprise.Enterprise;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnterpriseDashBoardProfileResponse {

    private int expertise;
    private int scheduleAdherence;
    private int initiative;
    private int communication;
    private int reEmploymentIntention;
    private double totalActiveScore;

    private String enterpriseType;

    private String bizContents;

    private Long sales;

    public static EnterpriseDashBoardProfileResponse of(Enterprise enterprise) {
        return EnterpriseDashBoardProfileResponse.builder()
                .expertise(enterprise.getWorkAssessment().getExpertise())
                .scheduleAdherence(enterprise.getWorkAssessment().getScheduleAdherence())
                .initiative(enterprise.getWorkAssessment().getInitiative())
                .communication(enterprise.getWorkAssessment().getCommunication())
                .reEmploymentIntention(enterprise.getWorkAssessment().getReEmploymentIntention())
                .totalActiveScore(enterprise.getWorkAssessment().getTotalActiveScore())
                .enterpriseType((enterprise.getSales() < 30_000_000_000L) ? "중소기업" : "중견기업")
                .bizContents(enterprise.getBizContents())
                .sales(enterprise.getSales())
                .build();
    }

}
