package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerDetailField;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PlannerResponse {
    private List<PlannerDetailField> plannerDetailFields;
    private String etcField;

    public static PlannerResponse of(Planner planner) {
        return new PlannerResponse(
                planner.getPlannerFields().stream()
                        .map(PlannerField::getPlannerDetailField)
                        .collect(Collectors.toList()),
                planner.getEtcField()
        );
    }
}
