package com.example.elancer.freelancerprofile.model.position.planner;

import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.PositionType;
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
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Planner extends Position {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlannerField> plannerFields = new ArrayList<>();

    private String etcField;

    public Planner(PositionType positionType, FreelancerProfile freelancerProfile) {
        super(positionType, freelancerProfile);
    }

    public static Planner createBasicPlanner(PositionType positionType, FreelancerProfile freelancerProfile) {
        return new Planner(positionType, freelancerProfile);
    }

    @Override
    public List<String> getAllSkillNames() {
        List<String> allSkillNames = new ArrayList<>();
        allSkillNames.addAll(plannerFields.stream()
                .map(field -> field.getPlannerDetailField().getDesc())
                .collect(Collectors.toList()));
        allSkillNames.add(etcField);

        return allSkillNames;
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
