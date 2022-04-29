package com.example.elancer.freelancerprofile.model.position.planner;

import com.example.elancer.common.model.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlannerField extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private PlannerDetailField plannerDetailField;

    @ManyToOne(fetch = FetchType.LAZY)
    private Planner planner;

    public PlannerField(PlannerDetailField plannerDetailField, Planner planner) {
        this.plannerDetailField = plannerDetailField;
        this.planner = planner;
    }

    public static PlannerField createPlannerField(PlannerDetailField plannerDetailField, Planner planner) {
        return new PlannerField(plannerDetailField, planner);
    }

    public void setPlanner(Planner planner) {
        this.planner = planner;
    }
}
