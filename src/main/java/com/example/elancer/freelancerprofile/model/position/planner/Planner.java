package com.example.elancer.freelancerprofile.model.position.planner;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.designer.DesignRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PLANNER")
public class Planner extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlannerField> plannerFields = new ArrayList<>();

    private String etcField;

    public Planner(FreelancerProfile freelancerProfile) {
        super(freelancerProfile);
    }

    public static Planner createBasicPlanner(FreelancerProfile freelancerProfile) {
        return new Planner(freelancerProfile);
    }

    public void coverAllField(List<PlannerField> plannerFields, String etcField) {
        coverPlannerFields(plannerFields);
        this.etcField = etcField;
    }

    private void coverPlannerFields(List<PlannerField> plannerFields) {
        this.plannerFields.clear();
        for (PlannerField plannerField : plannerFields) {
            plannerField.setPlanner(this);
        }
        this.plannerFields.addAll(plannerFields);
    }

}
