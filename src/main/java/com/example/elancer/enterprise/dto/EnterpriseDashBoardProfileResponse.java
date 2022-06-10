package com.example.elancer.enterprise.dto;


import com.example.elancer.common.model.WorkAssessment;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnterpriseDashBoardProfileResponse {

    private WorkAssessment workAssessment;

    private String enterpriseType;

    private String bizContents;

    private Long sales;

    public static EnterpriseDashBoardProfileResponse of(Enterprise enterprise) {
        return EnterpriseDashBoardProfileResponse.builder()
                .workAssessment(enterprise.getWorkAssessment())
                .enterpriseType((enterprise.getSales() < 30_000_000_000L) ? "중소기업" : "중견기업")
                .bizContents(enterprise.getBizContents())
                .sales(enterprise.getSales())
                .build();
    }

}
