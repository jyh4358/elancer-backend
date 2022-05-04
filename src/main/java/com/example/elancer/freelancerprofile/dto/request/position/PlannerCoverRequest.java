package com.example.elancer.freelancerprofile.dto.request.position;

import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerDetailField;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PlannerCoverRequest {
    @NotNull
    @Size(max = 3)
    private List<PlannerDetailField> plannerDetailFields;
    private String etcField;

    public List<PlannerField> toPlannerField(Planner planner) {
        if (plannerDetailFields == null) {
            return new ArrayList<>();
        }

        return plannerDetailFields.stream()
                .map(plannerDetailField -> PlannerField.createPlannerField(plannerDetailField, planner))
                .collect(Collectors.toList());
    }
}
